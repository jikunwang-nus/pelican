package jerryai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This class is designed to storage multiple ranges defined by [start,end)
 * and the end value is exclusive.
 * Methods addRange and removeRange are implemented
 * to update these ranges.
 * You can also get a full range representation by invoke toString
 *
 * @Author: Wonder
 * @Date: Created on 2023/3/25 13:58
 */
public class RangeList {
    private final List<Range> ranges = new ArrayList<>();


    static class Range {
        int start;
        int end;

        public int getStart() {
            return this.start;
        }

        Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

    }

    /**
     * check if input param valid, if not throw exception
     *
     * @param range param int array
     */
    private void validate(int[] range) {
        if (range.length != 2) {
            throw new RuntimeException("invalid range");
        }
        if (range[0] > range[1]) {
            throw new RuntimeException("invalid range");
        }
    }

    /**
     * sort key to guarantee start of range is sequential
     * add merge here to meet any merge demands
     */
    private void sort() {
        this.ranges.sort(Comparator.comparing(Range::getStart));
        merge();
    }

    /*
     * merge happens when add range
     * that is
     * if current data is [m,n)[n+1,k) when add a range like [n,n+1)
     * we should joint these as a new range [m,k)
     * */
    private void merge() {
        List<Range> remove = new ArrayList<>();
        for (int i = 0; i < ranges.size(); i++) {
            Range range = ranges.get(i);
            int tmp = i;
            while (tmp < ranges.size() - 1) {
                Range next = ranges.get(tmp + 1);
                if (next.start <= range.end + 1) {
                    range.end = next.end;
                    remove.add(next);
                    tmp++;
                } else {
                    break;
                }
            }
            i = tmp;
        }
        ranges.removeAll(remove);
    }

    /**
     * Adds a range to the list
     * @param array int[start,end] start included while end excluded
     * @Exception invalid range
     */
    public void addRange(int[] array) {
        validate(array);
        /*skip when end equals start*/
        if (array[0] == array[1]) return;
        /*wrap end -1 as truly border*/
        Range range = new Range(array[0], array[1] - 1);
        if (this.ranges.size() == 0) {
            this.ranges.add(range);
        } else {
            int min = ranges.get(0).start;
            int max = ranges.get(ranges.size() - 1).end;
            if (range.end < min || range.start > max) {
                if (range.end + 1 == min) {
                    ranges.get(0).start = range.start;
                } else if (range.start - 1 == max) {
                    ranges.get(ranges.size() - 1).end = range.end;
                } else {
                    ranges.add(range);
                    sort();
                }
                return;
            }
            for (int i = 0; i < ranges.size(); i++) {
                Range currentRange = ranges.get(i);
                /*case 1
                 * range is ahead of current range
                 * */
                if (currentRange.start > range.end) {
                    ranges.add(range);
                    break;
                }
                /*case 2
                 * exist cross and range end is ahead of current range end
                 * */
                if (currentRange.end >= range.end) {
                    currentRange.start = Math.min(range.start, currentRange.start);
                    break;
                }

                /*case 3
                    range start is after current range end
                * */
                if (currentRange.end < range.start) continue;

                /*case 4
                    exist cross, and in this case adjust range border for the next loop
                * */
                currentRange.start = Math.min(range.start, currentRange.start);
                if (i < ranges.size() - 1) {
                    int nextStart = ranges.get(i + 1).start;
                    if (nextStart > range.end) {
                        currentRange.end = range.end;
                        break;
                    } else {
                        currentRange.end = nextStart - 1;
                        range.start = nextStart;
                    }
                } else {
                    currentRange.end = range.end;
                }
            }
            sort();
        }
    }

    /**
     * Removes a range from the list
     * The key to solve removing is to calculate each cross between range and target range
     * only one thing to concern is split one range into two or remove range totally
     *
     * @param array input target range to remove
     */
    public void remove(int[] array) {
        validate(array);
        if (array[0] == array[1]) return;
        /*wrap end -1 as truly border*/
        Range range = new Range(array[0], array[1] - 1);
        if (this.ranges.size() == 0) {
            return;
        }
        int min = ranges.get(0).start;
        int max = ranges.get(ranges.size() - 1).end;
        if (range.end < min || range.start > max) return;
        range.start = Math.max(min, range.start);
        range.end = Math.min(max, range.end);
        List<Range> update = new ArrayList<>();
        int pointer = 0;
        for (int i = 0; i < ranges.size(); i++) {
            pointer = i;
            Range currentRange = ranges.get(i);
            /* when there is no cross just add current node to result
             * */
            if (currentRange.end < range.start) {
                update.add(currentRange);
                continue;
            }
            if (currentRange.start > range.end) {
                update.add(currentRange);
                continue;
            }
            /*when current range can hold target
             * */
            if (currentRange.start <= range.start && currentRange.end >= range.end) {
                /*here to split one into others*/
                if (currentRange.start < range.start) {
                    update.add(new Range(currentRange.start, range.start - 1));
                }
                if (currentRange.end > range.end) {
                    update.add(new Range(range.end + 1, currentRange.end));
                }
                break;
            }
            /* when target end is after of current range end
             * */
            if (currentRange.start <= range.start) {
                if (currentRange.start < range.start) {
                    update.add(new Range(currentRange.start, range.start - 1));
                }
                if (i < ranges.size() - 1) {
                    Range nextRange = ranges.get(i + 1);
                    if (nextRange.start > range.end) {
                        break;
                    } else {
                        range.start = nextRange.start;
                    }
                }
            }
        }
        /*handle left range when early break happens*/
        if (pointer < ranges.size() - 1) {
            update.addAll(ranges.subList(pointer + 1, ranges.size()));
        }
        /*
        just clear and load with update data
        * */
        this.ranges.clear();
        this.ranges.addAll(update);
        sort();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Range range : ranges) {
            sb.append("[")
                    .append(range.start)
                    .append(",")
                    .append(range.end + 1)
                    .append(")");
        }
        return sb.toString();
    }
}

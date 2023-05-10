package com.charsmart.data.bytecode.structure.attributes;

import com.charsmart.data.bytecode.structure.constantpool.ConstantPool;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/4 6:49 PM
 */
@Data
@Accessors(chain = true)
public class AttributeInfo {
    private int attributeNameIndex;
    private int attributeLength;

    private ConstantPool cp;

    AttributeInfo accept(DataInputStream is) throws IOException {
        attributeNameIndex = is.readUnsignedShort();
        attributeLength = is.readInt();
        return this;
    }

    public static AttributeInfo[] createAttrArray(ConstantPool cp, DataInputStream is) throws IOException {
        int attrCount = is.readUnsignedShort();
        AttributeInfo[] array = new AttributeInfo[attrCount];
        while (attrCount > 0) {
            array[array.length - attrCount] = AttributeInfo.instance(cp, is);
            attrCount--;
        }
        return array;
    }

    public static AttributeInfo instance(ConstantPool pool, DataInputStream is) throws IOException {
        int attributeNameIndex = is.readUnsignedShort();
        String type = pool.getUTF8Constant(attributeNameIndex);
        AttributeInfo attr;
        switch (type) {
            case ConstantValue:
                attr = new ConstantValueAttr().accept(is);
                break;
            case Code:
                attr = new CodeAttr().setCp(pool).accept(is);
                break;
            case StackMapTable:
                attr = new StackMapTableAttr().accept(is);
                break;
            case Exceptions:
                attr = new ExceptionsAttr().accept(is);
                break;
            case BootstrapMethods:
                attr = new BootstrapMethodsAttr().accept(is);
                break;
            case InnerClasses:
                attr = new InnerClassesAttr().accept(is);
                break;
            case EnclosingMethod:
                attr = new EnclosingMethodAttr().accept(is);
                break;
            case Synthetic:
                attr = new SyntheticAttr().accept(is);
                break;
            case Signature:
                attr = new SignatureAttr().accept(is);
                break;
            case RuntimeVisibleAnnotations:
                attr = new RuntimeVisibleAnnotationsAttr().accept(is);
                break;
            case RuntimeInvisibleAnnotations:
                attr = new RuntimeInvisibleAnnotationsAttr().accept(is);
                break;
            case RuntimeVisibleParameterAnnotations:
                attr = new RuntimeVisibleParameterAnnotationsAttr().accept(is);
                break;
            case RuntimeInvisibleParameterAnnotations:
                attr = new RuntimeInvisibleParameterAnnotationsAttr().accept(is);
                break;
            case RuntimeVisibleTypeAnnotations:
                attr = new RuntimeVisibleTypeAnnotationsAttr().accept(is);
                break;
            case RuntimeInvisibleTypeAnnotations:
                attr = new RuntimeInvisibleTypeAnnotationsAttr().accept(is);
                break;
            case AnnotationDefault:
                attr = new AnnotationDefaultAttr().accept(is);
                break;
            case MethodParameters:
                attr = new MethodParametersAttr().accept(is);
                break;
            case SourceFile:
                attr = new SourceFileAttr().accept(is);
                break;
            case SourceDebugExtension:
                attr = new SourceDebugExtensionAttr().accept(is);
                break;
            case LineNumberTable:
                attr = new LineNumberTableAttr().accept(is);
                break;
            case LocalVariableTable:
                attr = new LocalVariableTableAttr().accept(is);
                break;
            case LocalVariableTypeTable:
                attr = new LocalVariableTypeTableAttr().accept(is);
                break;
            case Deprecated:
                attr = new DeprecatedAttr().accept(is);
                break;
            default:
                throw new RuntimeException("invalid attribute type !");
        }
        return attr;
    }

    public static final String ConstantValue = "ConstantValue";
    private static final String Code = "Code";
    private static final String StackMapTable = "StackMapTable";
    private static final String Exceptions = "Exceptions";
    private static final String BootstrapMethods = "BootstrapMethods";
    private static final String InnerClasses = "InnerClasses";
    private static final String EnclosingMethod = "EnclosingMethod";
    private static final String Synthetic = "Synthetic";
    private static final String Signature = "Signature";
    private static final String RuntimeVisibleAnnotations = "RuntimeVisibleAnnotations";
    private static final String RuntimeInvisibleAnnotations = "RuntimeInvisibleAnnotations";
    private static final String RuntimeVisibleParameterAnnotations = "RuntimeVisibleParameterAnnotations";
    private static final String RuntimeInvisibleParameterAnnotations = "RuntimeInvisibleParameterAnnotations";
    private static final String RuntimeVisibleTypeAnnotations = "RuntimeVisibleTypeAnnotations";
    private static final String RuntimeInvisibleTypeAnnotations = "RuntimeInvisibleTypeAnnotations";
    private static final String AnnotationDefault = "AnnotationDefault";
    private static final String MethodParameters = "MethodParameters";
    private static final String SourceFile = "SourceFile";
    private static final String SourceDebugExtension = "SourceDebugExtension";
    private static final String LineNumberTable = "LineNumberTable";
    private static final String LocalVariableTable = "LocalVariableTable";
    private static final String LocalVariableTypeTable = "LocalVariableTypeTable";
    private static final String Deprecated = "Deprecated";
}

package com.charsmart.data.asm.boot;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.util.Set;

/**
 * @Author: Wonder
 * @Date: Created on 2022/8/12 5:08 PM
 */
public class AsmBootApplication {
    public static void main(String[] args) throws IOException {
        SimpleMetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        MetadataReader metadataReader = factory.getMetadataReader("com.charsmart.data.asm.boot.Simple");
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        Set<String> annotationTypes = annotationMetadata.getAnnotationTypes();
        Set<String> metaAnnotationTypes = annotationMetadata.getMetaAnnotationTypes("com.charsmart.data.asm.boot.annotation.Task");
        System.out.println(annotationTypes);
        System.out.println(metaAnnotationTypes);
    }
}

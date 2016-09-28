package com.fasterxml.classmate.members;

import java.util.List;

import junit.framework.TestCase;

import com.fasterxml.classmate.*;

// for issue "ghost" type parameter in field:
public class GhostTypeParameterInFieldTest
    extends TestCase
{
    public static class A<T extends Number> {
        public List<T> listOfT;
        public T t;
        public Integer i;
    }

    public void testGhostTypeParameterInField()
    {
        TypeResolver resolver = new TypeResolver();
        ResolvedType resolvedType = resolver.resolve(A.class, Integer.class);
        MemberResolver memberResolver = new MemberResolver(resolver);
        ResolvedTypeWithMembers resolvedTypeWithMembers = memberResolver.resolve(resolvedType, null, null);
        ResolvedField[] fields = resolvedTypeWithMembers.getMemberFields();

        // test fields
        assertEquals(3, fields.length);

        // field List<T> listOfT
        ResolvedField listOfT_Field = fields[0];
        assertEquals("listOfT", listOfT_Field.getName());
        ResolvedType listOfT_Type = listOfT_Field.getType();
        assertEquals(List.class, listOfT_Type.getErasedType());
        List<ResolvedType> listOfT_TypeParams = listOfT_Type.getTypeParameters();
        assertEquals("Expected 1 type paramter for listOfT, got " + listOfT_TypeParams.size() + ": " + listOfT_TypeParams, 1, listOfT_TypeParams.size());
        assertEquals(Integer.class, listOfT_TypeParams.get(0).getErasedType());

        // field t
        ResolvedField t_Field = fields[1];
        assertEquals("t", t_Field.getName());
        ResolvedType t_Type = t_Field.getType();
        assertEquals(Integer.class, t_Type.getErasedType());
        List<ResolvedType> t_TypeParams = t_Type.getTypeParameters();
        assertEquals("Expected 0 type paramter for t, got " + t_TypeParams.size() + ": " + t_TypeParams, 0, t_TypeParams.size());

        // field i
        ResolvedField i_Field = fields[2];
        assertEquals("i", i_Field.getName());
        ResolvedType i_Type = i_Field.getType();
        assertEquals(Integer.class, i_Type.getErasedType());
        List<ResolvedType> i_TypeParams = i_Type.getTypeParameters();
        assertEquals("Expected 0 type paramter for i, got " + i_TypeParams.size() + ": " + i_TypeParams, 0, i_TypeParams.size());
    }

}

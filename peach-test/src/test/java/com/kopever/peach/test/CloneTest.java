package com.kopever.peach.test;

import org.junit.Test;

public class CloneTest {

    @Test
    public void testBasicClone() {
        int[] a = {1, 2, 3};
        int[] b = a;
        System.out.println(a);
        System.out.println(b);
        System.out.println(a == b);

        System.out.println("---");

        int[] c = b.clone();
        System.out.println(c);
        System.out.println(a == c);
        System.out.println(b == c);
    }

    @Test
    public void testMultiClone() {
        try {
            Father a = new Father();
            a.name = "大仲马";
            a.age = 10086;
            a.child = new Child();
            a.child.name = "小仲马";
            a.child.age = 9527;

            Father b = (Father) a.clone();
            System.out.println(a == b);
            System.out.println(a.hashCode());
            System.out.println(b.hashCode());
            System.out.println(a.name);
            System.out.println(b.name);
            System.out.println(a.age);
            System.out.println(b.age);

            System.out.println("---");

            Father c = (Father) b.clone();
            System.out.println(a.child == c.child);
            System.out.println(a.child.name);
            System.out.println(c.child.age);
            System.out.println(a.child.hashCode());
            System.out.println(c.child.hashCode());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private class Father implements Cloneable {

        private String name;

        private int age;

        private Child child;

        protected Object clone() throws CloneNotSupportedException {
            Father f = (Father) super.clone();
            f.child = (Child) this.child.clone();
            return f;
        }

    }

    private class Child implements Cloneable {

        private String name;

        private int age;

        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

    }

}

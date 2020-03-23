package com.couragehe.util;

import java.util.BitSet;

/*
    简单的Bloom过滤器实现
 */
public class BloomUtils {
    private static final int SIZE = 1<<24;
    private  static BitSet bitSet=new BitSet(SIZE);
    private static Hash[] hashs=new Hash[8];
    private static final int seeds[]=new int[]{3,5,7,9,11,13,17,19};
    public BloomUtils(){
        for (int i = 0; i < seeds.length; i++) {
            hashs[i]=new Hash(seeds[i]);
        }
    }
    public void add(String string){
        for(Hash hash:hashs){
            bitSet.set(hash.getHash(string),true);
        }
    }
    public boolean contains(String string){
        boolean have=true;
        for(Hash hash:hashs){
            have&=bitSet.get(hash.getHash(string));
        }
        return have;
    }
    class Hash{
        private int seed = 0;
        public Hash(int seed){
            this.seed=seed;
        }
        public int getHash(String string){
            int val=0;
            int len=string.length();
            for (int i = 0; i < len; i++) {
                val=val*seed+string.charAt(i);
            }
            return val&(SIZE-1);
        }
    }
    public static void main(String[] args) {
    	String email="zhenlingcn@126.com";
    	BloomUtils bloomDemo=new BloomUtils();
    	System.out.println(email+"是否在列表中： "+bloomDemo.contains(email));
    	bloomDemo.add(email);
    	System.out.println(email+"是否在列表中： "+bloomDemo.contains(email));
    	email="zhenlingcn@163.com";
    	System.out.println(email+"是否在列表中： "+bloomDemo.contains(email));
    }
}
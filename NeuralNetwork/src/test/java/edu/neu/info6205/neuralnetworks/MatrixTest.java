package edu.neu.info6205.neuralnetworks;

import edu.neu.info6205.neuralnetworks.util.MathUtil;
import org.ejml.simple.SimpleMatrix;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MatrixTest {

    private double[] arr = {1.0,2.0,3.0,4.0};
    private double[][] arr2D = {arr};

    @Test
    public void testFromArray(){
        SimpleMatrix sm = MathUtil.fromArray(arr);
        assertEquals(sm.get(0,0),arr[0],1e-7);
        assertEquals(sm.get(1,0),arr[1],1e-7);
        assertEquals(sm.get(2,0),arr[2],1e-7);
        assertEquals(sm.get(3,0),arr[3],1e-7);
    }

    @Test
    public void testMap(){
        SimpleMatrix sm0 = MathUtil.fromArray(arr);
        SimpleMatrix sm = MathUtil.map(sm0,(x->x*2));
        assertEquals(sm.get(0,0),arr[0]*2,1e-7);
        assertEquals(sm.get(1,0),arr[1]*2,1e-7);
        assertEquals(sm.get(2,0),arr[2]*2,1e-7);
        assertEquals(sm.get(3,0),arr[3]*2,1e-7);
    }

    @Test
    public void testGetColumn(){
        SimpleMatrix sm0 = new SimpleMatrix(arr2D);
        double[] a = MathUtil.getColumn(sm0,0);
        assertEquals(a[0],1.0,1e-7);
        double[] b = MathUtil.getColumn(sm0,1);
        assertEquals(b[0],2.0,1e-7);
        double[] c = MathUtil.getColumn(sm0,2);
        assertEquals(c[0],3.0,1e-7);
        double[] d = MathUtil.getColumn(sm0,3);
        assertEquals(d[0],4.0,1e-7);

    }

    @Test
    public  void  testToArray(){
        SimpleMatrix sm0 = new SimpleMatrix(arr2D);
        double[][] res = MathUtil.toArray(sm0);
        assertTrue(Arrays.deepEquals(res,arr2D));
    }

}

package edu.neu.info6205.neuralnetworks;


import edu.neu.info6205.neuralnetworks.util.ActivationFunction;
import edu.neu.info6205.neuralnetworks.util.MathUtil;
import org.ejml.simple.SimpleMatrix;
import org.junit.Test;
import static org.junit.Assert.*;

public class FunctionTest {

    @Test
    public void TestSoftmax(){
        double[] test = {1000,2000,3000};
        SimpleMatrix sm = MathUtil.fromArray(test);
        sm = MathUtil.softMaxOutput(sm);
        double[] res = MathUtil.getColumn(sm,0);
        assertTrue(res[0]==0.0);
        assertTrue(res[1]==0.0);
        assertTrue(res[2]==1.0);
    }
    @Test
    public void TestSigmoid(){
        double test=1000.0;
        double res = ActivationFunction.sigmoidFunction.function(test);
        assertEquals(1.0, res, 1.0E-1);
    }
    @Test
    public void TestDSigmoid(){
        double test=1.0;
        double res = ActivationFunction.dSigmoidFunction.function(test);
        assertEquals(0, res,1e-7);
    }

    @Test
    public void TestRelu(){
        double test=0.5;
        double res = ActivationFunction.relu.function(test);
        assertEquals(0.5, res,1e-7);
    }
    @Test
    public void TestRelu2(){
        double test=-0.1;
        double res = ActivationFunction.relu.function(test);
        assertEquals(0.0, res,1e-7);
    }

    @Test
    public void TestDRelu(){
        double test=-0.1;
        double res = ActivationFunction.dRelu.function(test);
        assertEquals(0.0, res,1e-7);
    }

    @Test
    public void TestDRelu2(){
        double test=0.1;
        double res = ActivationFunction.dRelu.function(test);
        assertEquals(1.0, res,1e-7);
    }

    @Test
    public void TestTanh(){
        double test=1000;
        double res = ActivationFunction.tanh.function(test);
        assertEquals(1.0, res,1e-7);
    }
    @Test
    public void TestTanh2(){
        double test=0;
        double res = ActivationFunction.tanh.function(test);
        assertEquals(0.0, res,1e-7);
    }

    @Test
    public void TestDTanh(){
        double test=0.1;
        double res = ActivationFunction.dTanh.function(test);
        assertEquals(0.99, res,1e-7);
    }




}

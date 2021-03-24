import org.junit.jupiter.api.Test;

public class TestExample2 {

    private Example2 example2 = new Example2();

    @Test
    public void testOnNextDemo() {
        example2.onNextDemo();
    }

    @Test
    public void testOnErrorDemo() {
        example2.onErrorDemo();
    }

    @Test
    public void testIntermediateOpsDemo() {
        example2.intermediateOpsDemo();
    }

    @Test
    public void testObservableDotJustDemo() {
        example2.observableDotJustDemo();
    }

    @Test
    public void testObservableDotFromIterableDemo() {
        example2.observableDotFromIterable();
    }

    @Test
    public void testImplementingObserverDemo() {
        example2.implementingObserverDemo();
    }

    @Test
    public void testObserverWithLambdasDemo(){
        example2.observerWithLambdasDemo();
    }

    @Test
    public void testColdObservableDemo() {
        example2.coldObservableDemo();
    }

    @Test
    public void testConnectableObservableDemo() {
        example2.connectableObservableDemo();
    }

    @Test
    public void testObservableDotRangeDemo() {
        example2.observableDotRangeDemo();
    }

}

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;

public class TestExample1 {



    @Test
    public void testPrintObservable() {
        Example1 example1 = new Example1();
        Observable<String> observable = Observable.just("Jack", "Ed", "Bruce", "Alfred", "Steve");
        example1.printObservable(observable);
    }

    @Test
    public void testPrintStringLength() {
        Example1 example1 = new Example1();
        Observable<String> observable = Observable.just("Jack", "Ed", "Bruce", "Alfred", "Steve");
        example1.printStringLength(observable);
    }

    @Test
    public void testShowObservableStreamDifference() {
        Example1 example1 = new Example1();
        example1.showObservableStreamDifference();
    }


}

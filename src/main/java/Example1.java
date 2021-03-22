import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

import java.util.concurrent.TimeUnit;

public class Example1 {

    /*
    * An Observer<T> pushes data of type T. This is called emissions.
    * The emissions are useless unless they are subscribed by an Observer.
    * An Observer is typically a lambda function.
    * Emissions are pushed one at a time
    * */
    public void printObservable(Observable<String> input) {
        Consumer<String> consumer = s -> System.out.println(s);
        input.subscribe(consumer);
    }

    /*
    * There can be multiple operator between the Observable and Observer.
    * Each operator will return another new Observable.
    * The new Observable will reflect the result of the transformation
    * */
    public void printStringLength(Observable<String> input) {
        Consumer<Integer> consumer = s -> System.out.println(s);
        input.map(s -> s.length())
                .subscribe(consumer);
    }


    /*
    * The difference between an Observable and a Stream is that Observable is push-based and Stream is pull-based.
    * When it comes to pushing, we can push both data and events. In pull based, its only data.
    * The following snip shows how data and event are pushed with an interval of one second.
    * The idea is to see data as event and vice versa.
    * */
    public void showObservableStreamDifference() {
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS);
        Consumer<Long> observer = s -> System.out.println(s);
        observable.subscribe(observer);
        sleep(5000);
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

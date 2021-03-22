import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class Example2 {

    /*
    * An Observable works on 3 kinds of events: onNext(), onComplete() and onError().
    * onNext() passes each item down the stream to the Observer
    * onComplete() marks the end of the stream indicating there are no more items.
    * onError() indicates that there is some error occurred in the processing of the chain.
    * observer lambda is invoked on each item of the
    * */

    public void onNextDemo() {
        Observable<String> observable = Observable.create(observableEmitter -> {
           observableEmitter.onNext("Bruce");
            observableEmitter.onNext("Ed");
            observableEmitter.onNext("Tony");
            observableEmitter.onNext("Alfred");
            observableEmitter.onNext("Robin");
            observableEmitter.onComplete();
        });

        Consumer<String> observer = s -> System.out.println("RECIEVED => "+s);
        observable.subscribe(observer);

    }

}

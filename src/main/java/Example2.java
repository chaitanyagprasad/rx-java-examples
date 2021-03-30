import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observables.ConnectableObservable;

import java.util.Arrays;
import java.util.List;

public class Example2 {

    private final Observable<String> observableSource = Observable.just("Bruce", "Ed", "Tony", "Alfred", "Robin");
    private final Consumer<String> observerOne = s -> System.out.println("Observer 1 => "+s);
    private final Consumer<String> observerTwo = s -> System.out.println("Observer 2 => "+s);

    /*
    * An Observable works on 3 kinds of events: onNext(), onComplete() and onError().
    * onNext() passes each item down the stream to the Observer
    * onComplete() marks the end of the stream indicating there are no more items.
    * onError() indicates that there is some error occurred in the processing of the chain.
    * observer lambda is invoked on each item of the observable chain.    *
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

    /*
    * onError() method pushes the error error up the chain to be handled by the Observer.
    * */
    public void onErrorDemo() {
        Observable<String> observable = Observable.create(observableEmitter -> {
            try {
                observableEmitter.onNext("Bruce");
                observableEmitter.onNext("Ed");
                observableEmitter.onNext("Tony");
                observableEmitter.onNext("Alfred");
                observableEmitter.onNext("Robin");
                observableEmitter.onComplete();
            }catch (Exception e){
                observableEmitter.onError(e);
            }
        });

        observable.subscribe(s -> System.out.println("RECEIVED => "+s), Throwable::printStackTrace);
    }

    /*
    * The Observable may push the item directly to the observer or to an operator.
    * The operator will return an observable which incorporates the effect of the operation.
    * */
    public void intermediateOpsDemo() {

        Observable<String> observable = Observable.create(observableEmitter -> {
            try {
                observableEmitter.onNext("Bruce");
                observableEmitter.onNext("Ed");
                observableEmitter.onNext("Tony");
                observableEmitter.onNext("Alfred");
                observableEmitter.onNext("Robin");
                observableEmitter.onComplete();
            }catch (Exception e){
                observableEmitter.onError(e);
            }
        });

        Observable<Integer> integerObservable = observable.map(String::length);
        Observable<Integer> filteredObservable = integerObservable.filter(s -> s <= 4);

        Consumer<Integer> observer = s -> System.out.println("RECEIVED => "+s);
        filteredObservable.subscribe(observer);

    }

    /*
    * Observables can be created using Observable.just()
    * We can pass upto 10 items we want to emit and we can create the observable using the just()
    * */
    public void observableDotJustDemo() {
        Observable<String> observable = Observable.just("Bruce", "Ed", "Tony", "Alfred", "Robin");
        Consumer<String> observer = s -> System.out.println("RECIEVED => "+s);
        observable.subscribe(observer);
    }

    /*
    * Observables can be created from an Iterable object.
    * This can be done by using Observable.fromIterable()
    * */
    public void observableDotFromIterable(){
        List<String> list = Arrays.asList("Bruce", "Ed", "Tony", "Alfred", "Robin");
        Observable<String> observable = Observable.fromIterable(list);
        Consumer<String> observer = s -> System.out.println("RECIEVED => "+s);
        observable.subscribe(observer);
    }

    /*
    * The onNext(), onComplete(), and onError() methods actually define the Observer type.
    * When you call the subscribe() method on an Observable, an Observer is used to consume these three events by implementing its methods.
    * We can implement an Observer and pass an instance of it to the subscribe() method.
    * */
    public void implementingObserverDemo() {
        Observable<String> observable = Observable.just("Bruce", "Ed", "Tony", "Alfred", "Robin");

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("Received => "+integer);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done");
            }
        };

        observable.map(String::length).subscribe(observer);
    }

    /*
    * Implementing observer as in implementingObserverDemo() is cumbersome.
    * Therefore, we can use the subscribe() that takes lambdas as arguments.
    * While implementing the lambda, only the observer impl for onNext() is mandatory.
    * The subscribe() returns a Disposable. This Disposable object allow us to disconnect the observer and the observable.
    * */
    public void observerWithLambdasDemo() {
        Observable<String> observable = Observable.just("Bruce", "Ed", "Tony", "Alfred", "Robin");

        Consumer<Integer> onnext = s -> System.out.println("Received => "+s);
        Action onComplete = () -> System.out.println("Done");
        Consumer<Throwable> onError = Throwable::printStackTrace;

        observable.map(String::length).subscribe(onnext, onError, onComplete);

    }

    /*
    * A cold observable will replay all the emissions to each observer subscribing to it.
    * In the below example, the observable will first play all emissions to first observer, call onComplete() on it and repeat the same on the second observer.
    * Both observers receive the same dataset via separate streams.
    * Each observers are free to manipulate the emissions as the source will not be mutated.
    * Observables emitting finite data are cold in nature.
    * */
    public void coldObservableDemo() {

        this.observableSource.subscribe(observerOne);
        this.observableSource.subscribe(observerTwo);

        System.out.println("########################## Mutation demo ##########################");
        this.observableSource.subscribe(observerOne);
        this.observableSource.map(String::length).subscribe(s -> System.out.println("Observer 2 => "+s));

    }

    /*
    * A hot observable transmits emissions to all observers simultaneously.
    * If the observer A is not subscribed to an observable at the start of emissions but subscribes 5 secs after emissions begin, A will miss emissions done in the 5 secs.
    * Hot observables represent events. These events can carry some data with them.
    * A cold observable can be converted to a hot observable by using ConnectableObservable.
    * To convert a cold observable to a hot observable, we need to call publish() on the cold observable and it will yield a ConnectableObservable.
    * The connect() on the ConnectableObservable will start the emissions.
    * The ConnectableObservable forces emissions to go to all observers simultaneously. This is called multicasting.
    * ConnectableObservable prevents replay of data to each observer and avoids a performance overhead.
    * */
    public void connectableObservableDemo() {
        ConnectableObservable<String> connectableObservable = observableSource.publish();
        connectableObservable.subscribe(observerOne);
        connectableObservable.subscribe(observerTwo);
        connectableObservable.connect();
    }

    /*
    * Use Observable.range(start, emissionCount) to emit consecutive emissionCount number of integers from start(inclusive)
    * Use Observable.rangeLong(start, emissionCount) for larger numbers.
    * */
    public void observableDotRangeDemo() {
        Observable.range(5,5).subscribe(s -> System.out.println("Received => "+s) );
    }

    /*
    * Use Observable.empty() to create an empty observable
    * The output will be the contents of onComplete only.
    * An empty observable is RxJava concept of a null.
    * */
    public void observableDotEmptyDemo() {
        Observable<String> observable = Observable.empty();

        Consumer<String> onnext = s -> System.out.println("Received => "+s);
        Action onComplete = () -> System.out.println("Done");
        Consumer<Throwable> onError = Throwable::printStackTrace;

        observable.subscribe(onnext, onError, onComplete);
    }

    /*
    * If you want to never call onComplete() then use Observable.never().
    * This leaves the observers waiting forever for emission but there are no emissions made.
    * */
    public void observableDotNeverDemo() {
        Observable<String> observable = Observable.never();

        Consumer<String> onnext = s -> System.out.println("Received => "+s);
        Action onComplete = () -> System.out.println("Done");
        Consumer<Throwable> onError = Throwable::printStackTrace;

        observable.subscribe(onnext, onError, onComplete);
        sleep(5000);

    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

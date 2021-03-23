import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import java.util.Arrays;
import java.util.List;

public class Example2 {

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

}

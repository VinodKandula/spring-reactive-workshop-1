package ch.open;

import reactor.core.publisher.Flux;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Programmatically creating a sequence.
 */
public class Part08AdvancedOperators {

    /**
     * TODO 1
     * <p>
     * Create a sequence emitting items 1, 2 and then complete. Use {@link Flux#create(Consumer)}
     */
    public Flux<Integer> createFlux() {
        return Flux.create(subscriber -> {
            subscriber.next(1);
            subscriber.next(2);
            subscriber.complete();
        }); // TO BE REMOVED
    }

    /**
     * TODO 2
     * <p>
     * Create a Flux generating the sequence 0, 2, 4, 6, 8, 10, 12, 14 ...
     *
     * Look into {@link Flux#generate(Consumer)} operator.
     *
     * <p>
     * Examples:
     * nr = 0 --> empty sequence
     * nr = 1 --> sequence with 0
     * nr = 2 --> 0, 2
     * nr = 6 --> 0, 2, 4, 6, 8, 10
     * nr = 8 --> 0, 2, 4, 6, 8, 10, 12, 14
     */
    public Flux<Integer> generateFlux(int nr) {
        return Flux.generate(
                () -> 0,
                (state, sink) -> {
                    if (state == nr) {
                        sink.complete();
                    }
                    sink.next(2 * state);
                    return state + 1;
                });  // TO BE REMOVED
    }


    /**
     * TODO 3
     *
     * Use the {@link #alphabet} function to transform provided sequence. Note that the {@link #alphabet} function might
     * return nulls. The Reactive Streams specification disallows null values in a sequence.
     *
     * Look into {@link Flux#handle(BiConsumer)} to remove any nulls.
     */
    public Flux<String> removeNulls(Flux<Integer> flux) {
        return flux.handle((i, sink) -> {
            String letter = alphabet(i);
            if (letter != null) {
                sink.next(letter);
            }
        });  // TO BE REMOVED
    }

    public static String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
            return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
    }

}

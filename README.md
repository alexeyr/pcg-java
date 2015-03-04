# PCG-Java

This library implements [PCG](http://www.pcg-random.org/) random number generator (currently only one variant, same as in [the minimal C library](https://github.com/imneme/pcg-c-basic/)) in Java.

## Usage

Usage is very simple. `com.github.alexeyr.pcg.Pcg32` class has an interface very similar to `java.util.Random`:

    // creates the generator with a "random" seed; use Pcg32(long, long) to specify a seed instead.
    Pcg32 rnd = new Pcg32();
    int n = rnd.nextInt();
    // or nextInt(bound), or nextLong(), etc.

You can use `seed()` or `seed(long, long)` methods to reseed the generator at any time.

One important difference from `Random` is that `Pcg32` instances are _not_ thread-safe. A thread-safe variant may be added later.

## License

The library is available under the Apache 2.0 license, the same as PCG library.
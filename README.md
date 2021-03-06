# PCG-Java

[ ![Codeship Status for alexeyr/pcg-java](https://codeship.com/projects/6ce83e80-acd3-0132-f12e-16eb0c65b489/status?branch=master)](https://codeship.com/projects/68600)

This library implements [PCG](http://www.pcg-random.org/) random number generator (currently only one variant, same as in [the minimal C library](https://github.com/imneme/pcg-c-basic/)) in Java.

## Usage

Usage is very simple. `com.github.alexeyr.pcg.Pcg32` class has an interface very similar to `java.util.Random`:

    // creates the generator with a "random" seed; use Pcg32(long, long) to specify a seed instead.
    Pcg32 rnd = new Pcg32();
    int n = rnd.nextInt();
    // or nextInt(bound), or nextLong(), etc.

You can use `seed()` or `seed(long, long)` methods to reseed the generator at any time.

One important difference from `Random` is that `Pcg32` instances are _not_ thread-safe. A thread-safe variant may be added later.

## Downloading

[ ![Download](https://api.bintray.com/packages/alexeyr/maven/pcg-java/images/download.svg) ](https://bintray.com/alexeyr/maven/pcg-java/_latestVersion)

PCG-Java is available on [JCenter](https://bintray.com/bintray/jcenter), Sonatype OSS and Maven Central repositories. To use it in your projects, you should be able to just add the following to `pom.xml` (or the equivalent for your build system):
```
<dependencies>
  ...
  <dependency>
    <groupId>com.github.alexeyr.pcg</groupId>
    <artifactId>pcg</artifactId>
    <version>0.9.1</version> <!-- latest version number is listed above -->
  </dependency>
  ...
</dependencies>
```

Alternately, the code is simple enough that you can just include [`Pcg32.java`](pcg/src/main/java/com/github/alexeyr/pcg/Pcg32.java) in your code and modify it as desired.

## Dependencies

PCG-Java has no external dependencies, but if you want to run complete tests, your system needs to have `make` and a C compiler available.

## License

The library is available under the Apache 2.0 license, the same as PCG library.
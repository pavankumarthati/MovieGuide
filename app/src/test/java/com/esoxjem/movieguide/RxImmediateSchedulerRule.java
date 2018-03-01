package com.esoxjem.movieguide;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler.ExecutorWorker;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by pavankumar.thati on 3/1/18.
 */

public class RxImmediateSchedulerRule implements TestRule {

  private Scheduler immediate = new Scheduler() {

    @Override
    public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
      return super.scheduleDirect(run, 0, unit);
    }

    @Override
    public Worker createWorker() {
      return new ExecutorWorker(Runnable::run);
    }
  };


  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);

        try {
          base.evaluate();
        } finally {
          RxJavaPlugins.reset();
          RxAndroidPlugins.reset();
        }
      }
    };
  }
}

package com.demo.hope.flow;

import org.reactivestreams.Subscription;

import java.util.concurrent.Flow;

public class SubscriptionAdaptor implements Flow.Subscription, org.reactivestreams.Subscription {
    private Flow.Subscription flowSubscription;
    private org.reactivestreams.Subscription reactiveStreamsSubscription;

    private SubscriptionAdaptor(Flow.Subscription flowSubscription) {
        this.flowSubscription = flowSubscription;
    }

    private SubscriptionAdaptor(org.reactivestreams.Subscription reactiveStreamsSubscription) {
        this.reactiveStreamsSubscription = reactiveStreamsSubscription;
    }

    @Override
    public void request(long n) {
        ifTrueCall(flowSubscription != null, () -> flowSubscription.request(n));
        ifTrueCall(reactiveStreamsSubscription != null, () -> reactiveStreamsSubscription.request(n));
    }

    @Override
    public void cancel() {
        ifTrueCall(flowSubscription != null, () -> flowSubscription.cancel());
        ifTrueCall(reactiveStreamsSubscription != null, () -> reactiveStreamsSubscription.cancel());
    }

    private void ifTrueCall(boolean criteria, Runnable callThis) {
        if (criteria) {
            callThis.run();
        }
    }

    @Override
    public String toString() {
        return "SubscriptionAdaptor{" +
                "flowSubscription=" + flowSubscription +
                ", reactiveStreamsSubscription=" + reactiveStreamsSubscription +
                '}';
    }

    static Flow.Subscription toFlowSubscription(Subscription subscription) {
        return new SubscriptionAdaptor(subscription);
    }

    static Subscription toSubscription(Flow.Subscription subscription) {
        return new SubscriptionAdaptor(subscription);
    }
}

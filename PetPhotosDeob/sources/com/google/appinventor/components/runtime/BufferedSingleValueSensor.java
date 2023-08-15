package com.google.appinventor.components.runtime;

import android.hardware.SensorEvent;
import com.google.appinventor.components.annotations.SimpleObject;

@SimpleObject
/* loaded from: classes.dex */
public abstract class BufferedSingleValueSensor extends SingleValueSensor {
    private AveragingBuffer buffer;

    public BufferedSingleValueSensor(ComponentContainer container, int sensorType, int bufferSize) {
        super(container.$form(), sensorType);
        this.buffer = new AveragingBuffer(bufferSize);
    }

    @Override // com.google.appinventor.components.runtime.SingleValueSensor, android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (this.enabled && sensorEvent.sensor.getType() == this.sensorType) {
            float[] values = sensorEvent.values;
            this.buffer.insert(Float.valueOf(values[0]));
            super.onSensorChanged(sensorEvent);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public float getAverageValue() {
        return this.buffer.getAverage();
    }

    /* loaded from: classes.dex */
    private class AveragingBuffer {
        private Float[] data;
        private int next;

        private AveragingBuffer(int size) {
            this.data = new Float[size];
            this.next = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void insert(Float datum) {
            Float[] fArr = this.data;
            int i = this.next;
            this.next = i + 1;
            fArr[i] = datum;
            if (this.next == this.data.length) {
                this.next = 0;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public float getAverage() {
            double sum = 0.0d;
            int count = 0;
            for (int i = 0; i < this.data.length; i++) {
                if (this.data[i] != null) {
                    sum += this.data[i].floatValue();
                    count++;
                }
            }
            if (count != 0) {
                sum /= count;
            }
            return (float) sum;
        }
    }
}

package gnu.lists;

/* loaded from: classes.dex */
public interface PositionConsumer {
    void consume(SeqPosition seqPosition);

    void writePosition(AbstractSequence abstractSequence, int i);
}

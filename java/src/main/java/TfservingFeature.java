import java.util.List;

public class TfservingFeature {
    private List<Integer> inputs;
    private Integer drop_out;
    private List<Integer> sequence_length;

    @Override
    public String toString() {
        return "tfFeature{" +
                "inputs=" + inputs +
                ", drop_out=" + drop_out +
                ", sequence_length=" + sequence_length +
                '}';
    }

    public List<Integer> getInputs() {
        return inputs;
    }

    public void setInputs(List<Integer> inputs) {
        this.inputs = inputs;
    }

    public Integer getDrop_out() {
        return drop_out;
    }

    public void setDrop_out(Integer drop_out) {
        this.drop_out = drop_out;
    }

    public List<Integer> getSequence_length() {
        return sequence_length;
    }

    public void setSequence_length(List<Integer> sequence_length) {
        this.sequence_length = sequence_length;
    }
}

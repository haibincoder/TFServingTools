import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.tensorflow.framework.DataType;
import org.tensorflow.framework.TensorProto;
import org.tensorflow.framework.TensorShapeProto;
import tensorflow.serving.Model;
import tensorflow.serving.Predict;
import tensorflow.serving.PredictionServiceGrpc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8500).usePlaintext(true).build();
        // 这里使用block模式
        PredictionServiceGrpc.PredictionServiceBlockingStub stub = PredictionServiceGrpc.newBlockingStub(channel);
        // 创建请求
        Predict.PredictRequest.Builder predictRequestBuilder = Predict.PredictRequest.newBuilder();
        // 模型名称和模型方法名预设
        Model.ModelSpec.Builder modelSpecBuilder = Model.ModelSpec.newBuilder();
        modelSpecBuilder.setName("ner");
        modelSpecBuilder.setSignatureName("predict");
        predictRequestBuilder.setModelSpec(modelSpecBuilder);
        // 设置入参,访问默认是最新版本，如果需要特定版本可以使用tensorProtoBuilder.setVersionNumber方法
        List<Float> input = Arrays.asList(13f, 45f, 13f, 13f, 49f, 1f, 49f, 196f, 594f, 905f, 48f, 231f, 318f, 712f, 1003f, 477f, 259f, 291f, 287f, 161f, 65f, 62f, 82f, 68f, 2f, 10f);
        TensorProto.Builder inputTensorProto = TensorProto.newBuilder();
        inputTensorProto.setDtype(DataType.DT_FLOAT);
        inputTensorProto.addAllFloatVal(input);
        TensorShapeProto.Builder inputShapeBuilder = TensorShapeProto.newBuilder();
        inputShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1));
        inputShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1));
        inputShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(input.size()));
        inputTensorProto.setTensorShape(inputShapeBuilder.build());

        int dropout = 1;
        TensorProto.Builder dropoutTensorProto = TensorProto.newBuilder();
        dropoutTensorProto.setDtype(DataType.DT_INT32);
        dropoutTensorProto.addIntVal(dropout);

        List<Integer> seqLength = Collections.singletonList(26);
        TensorProto.Builder seqLengthTensorProto = TensorProto.newBuilder();
        seqLengthTensorProto.setDtype(DataType.DT_INT32);
        seqLengthTensorProto.addAllIntVal(seqLength);
        TensorShapeProto.Builder seqLengthShapeBuilder = TensorShapeProto.newBuilder();
        seqLengthShapeBuilder.addDim(TensorShapeProto.Dim.newBuilder().setSize(1));
        seqLengthTensorProto.setTensorShape(seqLengthShapeBuilder.build());

        predictRequestBuilder.putInputs("input", dropoutTensorProto.build());
        predictRequestBuilder.putInputs("drop_out", inputTensorProto.build());
        predictRequestBuilder.putInputs("sequence_length", seqLengthTensorProto.build());

        // 访问并获取结果
        Predict.PredictResponse predictResponse = stub.predict(predictRequestBuilder.build());
        org.tensorflow.framework.TensorProto result = predictResponse.toBuilder().getOutputsOrThrow("output");
        System.out.println("预测值是:"+result.getFloatValList());
    }
}

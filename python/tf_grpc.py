from grpc.beta import implementations
import tensorflow as tf

from tensorflow_serving.apis import predict_pb2, prediction_service_pb2_grpc

# 获取stub
channel = implementations.insecure_channel('localhost', 8500)
stub = prediction_service_pb2_grpc.PredictionServiceStub(channel._channel)

# 模型签名
request = predict_pb2.PredictRequest()
request.model_spec.name = 'ner'
# request.model_spec.version = 'latest'
request.model_spec.signature_name = 'predict'

# 构造入参
x_data = [[13, 45, 13, 13, 49, 1, 49, 196, 594, 905, 48, 231, 318, 712, 1003, 477, 259, 291, 287, 161, 65, 62, 82, 68, 2, 10]]
drop_out = 1
sequence_length = [26]
request.inputs['input'].CopyFrom(tf.make_tensor_proto(x_data, dtype=tf.int32))
request.inputs['sequence_length'].CopyFrom(tf.make_tensor_proto(sequence_length, dtype=tf.int32))
request.inputs['drop_out'].CopyFrom(tf.make_tensor_proto(drop_out, dtype=tf.float32))

result = stub.Predict(request, 10.0)  # 10 secs timeout

print(result)
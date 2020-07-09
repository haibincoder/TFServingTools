# tf_tools

blog:[https://www.cnblogs.com/bincoding/p/13266685.html](https://www.cnblogs.com/bincoding/p/13266685.html)

下载docker image:
docker pull tensorflow/serving

启动tfserving:
docker run -p 8501:8501 -p 8500:8500 -v /D/04_project/tf_tools/tf_serving/ner:/models/ner -e MODEL_NAME=ner -t tensorflow/serving  --rm   

查看tfserving状态：
http://localhost:8501/v1/models/ner
http://localhost:8501/v1/models/ner/metadata  可查看字段类型
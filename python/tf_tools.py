import tensorflow as tf
from tensorflow_core.contrib import slim

if __name__ == "__main__":
    model_path = 'tf_serving/ner/1'
    sess = tf.Session()
    meta_graph_def = tf.saved_model.loader.load(sess, ['serve'], model_path)
    graph = tf.get_default_graph()
    # slim.get_variables_by_name("d_")
    # sess.graph.get_tensor_by_name('input')
    print(meta_graph_def)

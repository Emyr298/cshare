from consumers.image_to_text import ImageToTextConsumer
from publishers import KafkaMessageProducer

if __name__ == "__main__":
    producer = KafkaMessageProducer()
    consumer = ImageToTextConsumer(producer)
    consumer.start()

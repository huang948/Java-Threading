import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Producer implements Runnable {

	List<String> buffer;
	
	Producer(List<String> buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		String[] numbers = {"1", "2", "3", "4", "5", "end"};
		for (String num : numbers) {
			synchronized (buffer) {
				System.out.println("Produced " + num);
				buffer.add(num);
				try {
					Random rand = new Random();
					Thread.sleep(rand.nextInt(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Finished producing");
	}
	
}

class Consumer implements Runnable {

	List<String> buffer;
	
	Consumer(List<String> buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		while (true) {
			synchronized (buffer) {
				if (buffer.isEmpty()) {
					System.out.println("Nothing to consume yet");
					continue;
				}
				if (buffer.get(0).equals("end")) {
					System.out.println("Finished Consuming");
					break;
				} else {
					System.out.println("Consumed " + buffer.remove(0));
					try {
						Random rand = new Random();
						Thread.sleep(rand.nextInt(2000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
}


public class ProducerConsumer {
	
    public static void main(String args[]) {
    	List<String> buffer = new ArrayList<String>();
    	
    	Thread producer = new Thread(new Producer(buffer));
    	Thread consumer = new Thread(new Consumer(buffer));
    	
    	producer.start();
    	consumer.start();
//    	
    }
}

class PrintSequenceABC implements Runnable {
		
		// static variables binded to the class
		static int PRINT_CHARS_UPTO = 30;
		static int count = 1;
		static Object lock = new Object();
		
		int print;
		
		PrintSequenceABC(int print) {
			this.print = print;
		}
		
		@Override
		public void run() {
			while (count < PRINT_CHARS_UPTO - 1) {
				synchronized (lock) {
					while (count % 3 != print) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.print(Thread.currentThread().getName());
					count++;
					lock.notifyAll();
				}
			}
		}
		
	}


public class ProgramABC {
	
	public static void main(String[] args) {
		PrintSequenceABC runnableSequenceA = new PrintSequenceABC(1);
		PrintSequenceABC runnableSequenceB = new PrintSequenceABC(2);
		PrintSequenceABC runnableSequenceC = new PrintSequenceABC(0);
		
		Thread t1 = new Thread(runnableSequenceA, "A");
		Thread t2 = new Thread(runnableSequenceB, "B");
		Thread t3 = new Thread(runnableSequenceC, "C");
		
		t1.start();
		t2.start();
		t3.start();
	}
		
}

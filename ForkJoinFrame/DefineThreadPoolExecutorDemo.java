package definethreadpoolexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class DefineThreadPoolExecutorDemo implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+" is running. ThreadId = "+Thread.currentThread().getId());
		try {
			Thread.sleep(1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} 
		System.out.println(Thread.currentThread().getName()+" finish.");
	}
	
	public static void main(String[] args) throws InterruptedException{
		DefineThreadPoolExecutorDemo threadpoolDemo = new DefineThreadPoolExecutorDemo();
		
		/*�½��̳߳�,���Զ��������
		 * �߳���Ϊ5;����߳���Ϊ5;�̳߳��߳�������corePoolSizeʱ,��������̴߳��ʱ��Ϊ0;ʹ�õ����޽����;
		 * ���һ��������дThreadFactory���newThread����,���̴߳���ʱ����Լ����߳�Id.
		 */
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(),
				new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				System.out.println("�����̣߳�"+t.getId());
				return t;
			}
		});
		
		for(int i=0; i<10; i++) {
			es.submit(threadpoolDemo);
		}
		
		Thread.sleep(5000);
		es.shutdown();
	}

}

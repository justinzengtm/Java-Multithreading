package forkjoinframe;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinFrameDemo {

	public static void main(String[] args) {
		ForkJoinPool forkjoinpool = new ForkJoinPool(); //ForkJoinPool�̳߳�
		ForkJoinDownloadTask task = new ForkJoinDownloadTask(0, 300000L); //ʵ������������
		ForkJoinTask<Long> taskResult = forkjoinpool.submit(task); //����������񷵻صĽ��
		
		try {
			long result = taskResult.get(); //��ȡÿ������������ؽ��
			System.out.println("completed! sum = "+result);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} catch(ExecutionException e) {
			e.printStackTrace();
		}

	}

}

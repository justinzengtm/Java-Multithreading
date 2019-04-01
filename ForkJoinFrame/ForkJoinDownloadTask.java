package forkjoinframe;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDownloadTask extends RecursiveTask<Long> {
	
	private static final long serialVersionUID = 1L;
	private long lowerBound; //��ʼ�ֽ�
	private long upperBound; //�����ֽ�
	private long dataSum; //(��)����������
	private long sum = 0;
	
	public ForkJoinDownloadTask(long lowerBound, long upperBound) {
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	@Override
	protected Long compute() {
		
		if((upperBound-lowerBound)<10000) {
			for(long i=lowerBound; i<=upperBound; i++) {
				sum +=i; //ģ����������
			}
		} else {
			//����ļ�̫��,�ͽ��зָ��
			long part = (upperBound-lowerBound)/100; //��¼�ָ��100����,ÿ�����ļ��Ĵ�С
			long startLocation = lowerBound; //initLocation��¼����ĳ�ʼ��ʼλ��
			//�����100��������Ķ���
			ArrayList<ForkJoinDownloadTask> downloadTask = new ArrayList<ForkJoinDownloadTask>(); 
			
			//�ѷֳɵ�100���������ύ��������ȥ
			for(int i=0; i<100; i++) {
				long endLocation = startLocation+part; //��¼������������ؽ���λ��
				//��������ж�,�����������Ľ���λ�ô���������Ľ���λ��,����ǰ�����������һ����.
				if(endLocation>upperBound) {
					//��������Ľ���λ�õ����������С�Ľ���λ��.
					endLocation = upperBound;
				}
				
				//ʵ����������
				ForkJoinDownloadTask downloadtask = new ForkJoinDownloadTask(startLocation, endLocation);
				//������������Ϣ
				downloadtask.dataSum = downloadtask.upperBound - downloadtask.lowerBound; //�������������������
				sum+=downloadtask.dataSum; //sum������������
				System.out.println("Task - "+i+"�� startLocation="+downloadtask.lowerBound+", "
						+ "endLocation="+downloadtask.upperBound);
				System.out.println("dataSum="+downloadtask.dataSum+"  sum="+sum+"\r\n");
								
				startLocation+=part; //��һ��������Ŀ�ʼλ��
				downloadTask.add(downloadtask); //�ѵ�ǰ����������������
				downloadtask.fork(); //Ϊ�����񴴽���֧
			}
			//�����������ύ������������
			
			for(ForkJoinDownloadTask t:downloadTask) {
				t.join(); //join()�ȴ�����,��"�ȴ��������������"
			}
		}
		
		return sum;
	}
}

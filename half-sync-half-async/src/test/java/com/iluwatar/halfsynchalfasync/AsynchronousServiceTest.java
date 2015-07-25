package com.iluwatar.halfsynchalfasync;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Test;
import static org.junit.Assert.*;

public class AsynchronousServiceTest {

	@Test
	public void test() throws InterruptedException, ExecutionException {
		/*
		 * Addition service is asynchronous layer which does not block on single request,
		 * and is always available for listening new requests.
		 */
		ArithmeticSumService service = new ArithmeticSumService();
		Future<Long> output1 = service.execute(100L);
		Future<Long> output2 = service.execute(50L);
		Future<Long> output3 = service.execute(200L);
		Future<Long> output4 = service.execute(5L);
		
		assertEquals(ap(100), output1.get().longValue());
		assertEquals(ap(50), output2.get().longValue());
		assertEquals(ap(200), output3.get().longValue());
		assertEquals(ap(5), output4.get().longValue());
	}
	
	/*
	 * This is an asynchronous service which computes arithmetic sum
	 */
	class ArithmeticSumService extends AsynchronousService<Long, Long> {

		@Override
		protected Long doInBackground(Long n) {
			return (n) * (n + 1) / 2;
		}
	}

	private long ap(int i) {
		long out = (i) * (i + 1) / 2;
		System.out.println(out);
		return out;
	}
}

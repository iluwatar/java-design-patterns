package com.iluwatar.halfsynchalfasync;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class AsynchronousServiceTest {

	@Test
	public void test() throws InterruptedException, ExecutionException {
		/*
		 * Addition service is asynchronous layer which does not block on single request,
		 * and is always available for listening new requests.
		 */
		QueuingLayer queuingLayer = new QueuingLayer();
		new SynchronousLayer(queuingLayer);
		AsynchronousService service = new AsynchronousService(queuingLayer);
		
		service.execute(new ArithmeticSumTask(100));
		service.execute(new ArithmeticSumTask(50));
		service.execute(new ArithmeticSumTask(200));
		service.execute(new ArithmeticSumTask(5));
	}
	
	class ArithmeticSumTask implements AsyncTask<Long> {
		private long n;

		public ArithmeticSumTask(long n) {
			this.n = n;
		}
		
		@Override
		public Long call() throws Exception {
			return ap(n);
		}

		@Override
		public void preExecute() {
			if (n < 0) {
				throw new IllegalArgumentException("n is less than 0");
			}
		}

		@Override
		public void onResult(Long result) {
			assertEquals(ap(n), result.longValue());
		}

		@Override
		public void onError(Throwable throwable) {
			fail("Should not occur");
		}
	}
	
	private long ap(long i) {
		long out = (i) * (i + 1) / 2;
		return out;
	}
}

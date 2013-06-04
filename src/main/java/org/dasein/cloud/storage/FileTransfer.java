/**
 * Copyright (C) 2009-2013 Dell, Inc.
 *
 * ====================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ====================================================================
 */

package org.dasein.cloud.storage;

import org.dasein.cloud.AsynchronousTask;

public class FileTransfer extends AsynchronousTask<Object> {
	private long             bytesToTransfer;
	private long             bytesTransferred;
	
	public FileTransfer() { } 
	
	public long getBytesToTransfer() {
		return bytesToTransfer;
	}

	public void setBytesToTransfer(long bytesToTransfer) {
		this.bytesToTransfer = bytesToTransfer;
	}

	public long getBytesTransferred() {
		return bytesTransferred;
	}

	public void setBytesTransferred(long bytesTransferred) {
		this.bytesTransferred = bytesTransferred;
		setPercentComplete(((double)bytesTransferred)/((double)bytesToTransfer));
	}

	public Throwable getTransferError() {
		return getTaskError();
	}
	
	public double getTransferRateInKilobytesPerSecond() {
		return ((double)getBytesTransferred())/((double)getDuration());
	}
}

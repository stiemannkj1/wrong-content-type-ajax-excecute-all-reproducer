/**
 * Copyright (c) 2000-2018 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.wrong.content.type.ajax.excecute.all.reproducer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextWrapper;

/**
 *
 * @author Kyle Stiemann
 */
public class ExternalContextImpl extends ExternalContextWrapper {

	private final ExternalContext wrappedExternalContext;
	private final CopyOnWriteArrayList<String> externalContextCalls;

	public ExternalContextImpl(ExternalContext wrappedExternalContext) {
		this.wrappedExternalContext = wrappedExternalContext;
		this.externalContextCalls = new CopyOnWriteArrayList<String>();
	}

	@Override
	public void setResponseContentType(String contentType) {
		externalContextCalls.add("setResponseContentType(\"" + contentType + "\")");
		super.setResponseContentType(contentType);
	}

	@Override
	public Writer getResponseOutputWriter() throws IOException {
		externalContextCalls.add("getResponseOutputWriter()");
		return super.getResponseOutputWriter();
	}

	@Override
	public OutputStream getResponseOutputStream() throws IOException {
		externalContextCalls.add("getResponseOutputStream()");
		return super.getResponseOutputStream();
	}

	public List<String> getExternalContextCalls() {
		return externalContextCalls;
	}

	@Override
	public ExternalContext getWrapped() {
		return wrappedExternalContext;
	}

	
}

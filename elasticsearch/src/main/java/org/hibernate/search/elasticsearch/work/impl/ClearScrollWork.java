/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.search.elasticsearch.work.impl;

import org.hibernate.search.elasticsearch.work.impl.builder.ClearScrollWorkBuilder;

import io.searchbox.action.Action;
import io.searchbox.client.JestResult;
import io.searchbox.core.SearchScroll;

/**
 * @author Yoann Rodiere
 */
public class ClearScrollWork extends SimpleElasticsearchWork<JestResult, Void> {

	protected ClearScrollWork(Builder builder) {
		super( builder );
	}

	@Override
	protected Void generateResult(ElasticsearchWorkExecutionContext context, JestResult response) {
		return null;
	}

	public static class Builder
			extends SimpleElasticsearchWork.Builder<Builder, JestResult>
			implements ClearScrollWorkBuilder {
		private final SearchScroll.Builder jestBuilder;

		public Builder(String scrollId) {
			super( null, DefaultElasticsearchRequestSuccessAssessor.INSTANCE, NoopElasticsearchWorkSuccessReporter.INSTANCE );
			this.jestBuilder = new SearchScroll.Builder( scrollId, "" );
		}

		@Override
		protected Action<JestResult> buildAction() {
			return new ClearScrollAction( jestBuilder );
		}

		@Override
		public ClearScrollWork build() {
			return new ClearScrollWork( this );
		}
	}

	private static class ClearScrollAction extends SearchScroll {
		protected ClearScrollAction(Builder builder) {
			super( builder );
		}

		@Override
		public String getRestMethodName() {
			return "DELETE";
		}
	}
}
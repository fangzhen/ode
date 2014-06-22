/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.ode.bpel.obj;

import org.apache.ode.bpel.obj.OScope.Variable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Base model class for forEach activity.
 */
public class OForEach extends OActivity {
	private static final String COUNTERVARIABLE = "counterVariable";
	private static final String PARALLEL = "parallel";
	private static final String STARTCOUNTERVALUE = "startCounterValue";
	private static final String FINALCOUNTERVALUE = "finalCounterValue";
	private static final String COMPLETIONCONDITION = "completionCondition";
	private static final String INNERSCOPE = "innerScope";

	public OForEach(OProcess owner, OActivity parent) {
		super(owner, parent);
	}

	@JsonIgnore
	public CompletionCondition getCompletionCondition() {
		return (CompletionCondition) fieldContainer.get(COMPLETIONCONDITION);
	}

	@JsonIgnore
	public Variable getCounterVariable() {
		return (Variable) fieldContainer.get(COUNTERVARIABLE);
	}

	@JsonIgnore
	public OExpression getFinalCounterValue() {
		return (OExpression) fieldContainer.get(FINALCOUNTERVALUE);
	}

	@JsonIgnore
	public OScope getInnerScope() {
		return (OScope) fieldContainer.get(INNERSCOPE);
	}

	@JsonIgnore
	public boolean isParallel() {
		return (boolean) fieldContainer.get(PARALLEL);
	}

	@JsonIgnore
	public OExpression getStartCounterValue() {
		return (OExpression) fieldContainer.get(STARTCOUNTERVALUE);
	}

	public void setCompletionCondition(CompletionCondition completionCondition) {
		fieldContainer.put(COMPLETIONCONDITION, completionCondition);
	}

	public void setCounterVariable(Variable counterVariable) {
		fieldContainer.put(COUNTERVARIABLE, counterVariable);
	}

	public void setFinalCounterValue(OExpression finalCounterValue) {
		fieldContainer.put(FINALCOUNTERVALUE, finalCounterValue);
	}

	public void setInnerScope(OScope innerScope) {
		fieldContainer.put(INNERSCOPE, innerScope);
	}

	public void setParallel(boolean parallel) {
		fieldContainer.put(PARALLEL, parallel);
	}

	public void setStartCounterValue(OExpression startCounterValue) {
		fieldContainer.put(STARTCOUNTERVALUE, startCounterValue);
	}

	public String toString() {
		return "+{OForEach : "
				+ getName()
				+ ", counterName="
				+ getCounterVariable().getName()
				+ ", parallel="
				+ isParallel()
				+ ", startCounterValue="
				+ getStartCounterValue()
				+ ", finalCounterValue="
				+ getFinalCounterValue()
				+ ", completionCondition="
				+ (getCompletionCondition() == null ? ""
						: getCompletionCondition()) + "}";
	}

	public static class CompletionCondition extends OBase {
		private static final String SUCCESSFULBRANCHESONLY = "successfulBranchesOnly";
		private static final String BRANCHCOUNT = "branchCount";

		public CompletionCondition(OProcess owner) {
			super(owner);
		}

		@JsonIgnore
		public OExpression getBranchCount() {
			return (OExpression) fieldContainer.get(BRANCHCOUNT);
		}

		@JsonIgnore
		public boolean getSuccessfulBranchesOnly() {
			return (boolean) fieldContainer.get(SUCCESSFULBRANCHESONLY);
		}

		public void setBranchCount(OExpression branchCount) {
			fieldContainer.put(BRANCHCOUNT, branchCount);
		}

		public void setSuccessfulBranchesOnly(boolean successfulBranchesOnly) {
			fieldContainer.put(SUCCESSFULBRANCHESONLY, successfulBranchesOnly);
		}
	}
}
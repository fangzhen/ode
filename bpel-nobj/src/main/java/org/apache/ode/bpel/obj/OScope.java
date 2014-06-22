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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ode.bpel.obj.OProcess.OProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Compiled representation of a BPEL scope. Instances of this class
 * are generated by the BPEL compiler.
 */
public class OScope extends OActivity {

	/** Name of the scope. */
	private static final String NAME = "name";

	/** ScopeLikeConstructImpl's fault handler. */
	private static final String FAULTHANDLER = "faultHandler";

	/** The activity that gets executed within this scope. */
	private static final String ACTIVITY = "activity";

	/** ScopeLikeConstructImpl's compensation handler. */
	private static final String COMPENSATIONHANDLER = "compensationHandler";

	/** ScopeLikeConstructImpl's termination handler. */
	private static final String TERMINATIONHANDLER = "terminationHandler";

	/** ScopeLikeConstructImpl's event handler. */
	private static final String EVENTHANDLER = "eventHandler";

	/** Variables declared within the scope. */
	private static final String VARIABLES = "variables";

	/** OCorrelation sets declared within the scope. */
	private static final String CORRELATIONSETS = "correlationSets";

	private static final String PARTNERLINKS = "partnerLinks";

	/** The descendants of this scope that can be compensated from the FH/CH of this scope. */
	private static final String COMPENSATABLE = "compensatable";

	private static final String IMPLICITSCOPE = "implicitScope";

	/** Is this scope <em>atomic</em> i.e. meant to execute in a single transaction. */
	private static final String ATOMICSCOPE = "atomicScope";

	/** Is this scope <em>isolated</em> i.e. protected against concurrent access to its variables. */
	private static final String ISOLATEDSCOPE = "isolatedScope";

	public OScope(OProcess owner, OActivity parent) {
		super(owner, parent);
		setVariables(new HashMap<String, Variable>());
		setCorrelationSets(new HashMap<String, CorrelationSet>());
		setPartnerLinks(new HashMap<String, OPartnerLink>());
		setCompensatable(new HashSet<OScope>());
	}

	public void addCorrelationSet(CorrelationSet ocset) {
		getCorrelationSets().put(getName(), ocset);
	}

	public void addLocalVariable(Variable variable) {
		getVariables().put(getName(), variable);
	}

	@Override
	public void dehydrate() {
		super.dehydrate();
		setActivity(null);
		if (getCompensatable() != null) {
			getCompensatable().clear();
		}
		if (getCompensationHandler() != null) {
			getCompensationHandler().dehydrate();
			setCompensationHandler(null);
		}
		if (getTerminationHandler() != null) {
			getTerminationHandler().dehydrate();
			setTerminationHandler(null);
		}
		if (getEventHandler() != null) {
			getEventHandler().dehydrate();
			setEventHandler(null);
		}
		if (getVariables() != null) {
			getVariables().clear();
		}
		if (getCorrelationSets() != null) {
			getCorrelationSets().clear();
		}
		if (getPartnerLinks() != null) {
			getPartnerLinks().clear();
		}
	}

	@JsonIgnore
	public OActivity getActivity() {
		return (OActivity) fieldContainer.get(ACTIVITY);
	}

	@JsonIgnore
	public boolean isAtomicScope() {
		return (boolean) fieldContainer.get(ATOMICSCOPE);
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	public Set<OScope> getCompensatable() {
		return (Set<OScope>) fieldContainer.get(COMPENSATABLE);
	}

	@JsonIgnore
	public OCompensationHandler getCompensationHandler() {
		return (OCompensationHandler) fieldContainer.get(COMPENSATIONHANDLER);
	}

	/**
	 * Obtains the correlation set visible in current scope or parent scope.
	 *
	 * @param corrName correlation set name
	 *
	 * @return
	 */
	public CorrelationSet getCorrelationSet(String corrName) {
		return getCorrelationSets().get(corrName);
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	public Map<String, CorrelationSet> getCorrelationSets() {
		return (Map<String, CorrelationSet>) fieldContainer
				.get(CORRELATIONSETS);
	}

	@JsonIgnore
	public OEventHandler getEventHandler() {
		return (OEventHandler) fieldContainer.get(EVENTHANDLER);
	}

	@JsonIgnore
	public OFaultHandler getFaultHandler() {
		return (OFaultHandler) fieldContainer.get(FAULTHANDLER);
	}

	@JsonIgnore
	public boolean isImplicitScope() {
		return (boolean) fieldContainer.get(IMPLICITSCOPE);
	}

	@JsonIgnore
	public boolean isIsolatedScope() {
		return (boolean) fieldContainer.get(ISOLATEDSCOPE);
	}

	public OPartnerLink getLocalPartnerLink(String name) {
		return getPartnerLinks().get(name);
	}

	/**
	 *
	 * Get a localy-defined variable by name.
	 * @param varName name of variable
	 *
	 * @return
	 */
	public Variable getLocalVariable(final String varName) {
		return getVariables().get(varName);
	}

	@JsonIgnore
	public String getName() {
		return (String) fieldContainer.get(NAME);
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	public Map<String, OPartnerLink> getPartnerLinks() {
		return (Map<String, OPartnerLink>) fieldContainer.get(PARTNERLINKS);
	}

	@JsonIgnore
	public OTerminationHandler getTerminationHandler() {
		return (OTerminationHandler) fieldContainer.get(TERMINATIONHANDLER);
	}

	@SuppressWarnings("unchecked")
	@JsonIgnore
	public HashMap<String, Variable> getVariables() {
		return (HashMap<String, Variable>) fieldContainer.get(VARIABLES);
	}

	public OPartnerLink getVisiblePartnerLink(String name) {
		OActivity current = this;
		OPartnerLink plink;
		while (current != null) {
			if (current instanceof OScope) {
				plink = ((OScope) current).getLocalPartnerLink(name);
				if (plink != null)
					return plink;
			}
			current = current.getParent();
		}
		return null;
	}

	public Variable getVisibleVariable(String varName) {
		OActivity current = this;
		Variable variable;
		while (current != null) {
			if (current instanceof OScope) {
				variable = ((OScope) current).getLocalVariable(varName);
				if (variable != null)
					return variable;
			}
			current = current.getParent();
		}
		return null;
	}

	public boolean isInAtomicScope() {
		OActivity current = this;
		while (current != null) {
			if (current instanceof OScope && isAtomicScope())
				return true;
			current = current.getParent();
		}
		return false;
	}

	public void setActivity(OActivity activity) {
		fieldContainer.put(ACTIVITY, activity);
	}

	public void setAtomicScope(boolean atomicScope) {
		fieldContainer.put(ATOMICSCOPE, atomicScope);
	}

	public void setCompensatable(Set<OScope> compensatable) {
		if (getCompensatable() == null) {
			fieldContainer.put(COMPENSATABLE, compensatable);
		}
	}

	public void setCompensationHandler(OCompensationHandler compensationHandler) {
		fieldContainer.put(COMPENSATIONHANDLER, compensationHandler);
	}

	public void setCorrelationSets(Map<String, CorrelationSet> correlationSets) {
		if (getCorrelationSets() == null) {
			fieldContainer.put(CORRELATIONSETS, correlationSets);
		}
	}

	public void setEventHandler(OEventHandler eventHandler) {
		fieldContainer.put(EVENTHANDLER, eventHandler);
	}

	public void setFaultHandler(OFaultHandler faultHandler) {
		fieldContainer.put(FAULTHANDLER, faultHandler);
	}

	public void setImplicitScope(boolean implicitScope) {
		fieldContainer.put(IMPLICITSCOPE, implicitScope);
	}

	public void setIsolatedScope(boolean isolatedScope) {
		fieldContainer.put(ISOLATEDSCOPE, isolatedScope);
	}

	public void setName(String name) {
		fieldContainer.put(NAME, name);
	}

	public void setPartnerLinks(Map<String, OPartnerLink> partnerLinks) {
		if (getPartnerLinks() == null) {
			fieldContainer.put(PARTNERLINKS, partnerLinks);
		}
	}

	public void setTerminationHandler(OTerminationHandler terminationHandler) {
		fieldContainer.put(TERMINATIONHANDLER, terminationHandler);
	}

	public void setVariables(HashMap<String, Variable> variables) {
		if (getVariables() == null) {
			fieldContainer.put(VARIABLES, variables);
		}
	}

	public String toString() {
		return "{OScope '" + getName() + "' id=" + getId() + "}";
	}

	public static final class CorrelationSet extends OBase {

		private static final String NAME = "name";
		private static final String DECLARINGSCOPE = "declaringScope";
		private static final String PROPERTIES = "properties";

		/**
		 * Indicates that this correlation set has a join use case in the scope.
		 */
		private static final String HASJOINUSECASES = "hasJoinUseCases";

		public CorrelationSet(OProcess owner) {
			super(owner);
			setProperties(new ArrayList<OProcess.OProperty>());

		}

		@JsonIgnore
		public OScope getDeclaringScope() {
			return (OScope) fieldContainer.get(DECLARINGSCOPE);
		}

		@JsonIgnore
		public boolean getHasJoinUseCases() {
			return (boolean) fieldContainer.get(HASJOINUSECASES);
		}

		@JsonIgnore
		public String getName() {
			return (String) fieldContainer.get(NAME);
		}

		@SuppressWarnings("unchecked")
		@JsonIgnore
		public List<OProperty> getProperties() {
			return (List<OProperty>) fieldContainer.get(PROPERTIES);
		}

		public void setDeclaringScope(OScope declaringScope) {
			fieldContainer.put(DECLARINGSCOPE, declaringScope);
		}

		public void setHasJoinUseCases(boolean hasJoinUseCases) {
			fieldContainer.put(HASJOINUSECASES, hasJoinUseCases);
		}

		public void setName(String name) {
			fieldContainer.put(NAME, name);
		}

		public void setProperties(List<OProperty> properties) {
			fieldContainer.put(PROPERTIES, properties);
		}

		public String toString() {
			return "{CSet " + getName() + " " + getProperties() + "}";
		}
	}

	public static final class Variable extends OBase {
		private static final String NAME = "name";
		private static final String DECLARINGSCOPE = "declaringScope";
		private static final String TYPE = "type";
		/** If not-null indicates that this variable has an external representation. */
		private static final String EXTVAR = "extVar";

		public Variable(OProcess owner, OVarType type) {
			super(owner);
			setType(type);
		}

		@JsonIgnore
		public OScope getDeclaringScope() {
			return (OScope) fieldContainer.get(DECLARINGSCOPE);
		}

		public String getDescription() {
			StringBuffer buf = new StringBuffer(getDeclaringScope().getName());
			buf.append('.');
			buf.append(getName());
			return buf.toString();
		}

		@JsonIgnore
		public OExtVar getExtVar() {
			return (OExtVar) fieldContainer.get(EXTVAR);
		}

		@JsonIgnore
		public String getName() {
			return (String) fieldContainer.get(NAME);
		}

		@JsonIgnore
		public OVarType getType() {
			return (OVarType) fieldContainer.get(TYPE);
		}

		public void setDeclaringScope(OScope declaringScope) {
			fieldContainer.put(DECLARINGSCOPE, declaringScope);
		}

		public void setExtVar(OExtVar extVar) {
			fieldContainer.put(EXTVAR, extVar);
		}

		public void setName(String name) {
			fieldContainer.put(NAME, name);
		}

		public void setType(OVarType type) {
			fieldContainer.put(TYPE, type);
		}

		public String toString() {
			return "{Variable " + getDescription() + ":" + getType() + "}";
		}
	}

}

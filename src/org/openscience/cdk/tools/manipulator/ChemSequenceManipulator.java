/* $RCSfile$
 * $Author$ 
 * $Date$
 * $Revision$
 * 
 * Copyright (C) 2003-2006  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *  */
package org.openscience.cdk.tools.manipulator;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IChemSequence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class with convenience methods that provide methods from
 * methods from ChemObjects within the ChemSequence.
 *
 * @see org.openscience.cdk.AtomContainer#removeAtomAndConnectedElectronContainers(IAtom)
 *
 * @cdk.module standard
 */
public class ChemSequenceManipulator {

    public static int getAtomCount(IChemSequence sequence) {
    	int count = 0;
        for (int i=0; i<sequence.getChemModelCount(); i++) {
        	count += ChemModelManipulator.getAtomCount(sequence.getChemModel(i));
        }
        return count;
    }

    public static int getBondCount(IChemSequence sequence) {
    	int count = 0;
        for (int i=0; i<sequence.getChemModelCount(); i++) {
        	count += ChemModelManipulator.getBondCount(sequence.getChemModel(i));
        }
        return count;
    }

    /**
     * Puts all the Molecules of this container together in one 
     * AtomCcntainer.
     *
     * @return  The AtomContainer with all the Molecules of this container
     * 
     * @deprecated This method has a serious performace impact. Try to use
     *   other methods.
     */
    public static IAtomContainer getAllInOneContainer(IChemSequence sequence) {
        IAtomContainer container = sequence.getBuilder().newAtomContainer();
        for (int i = 0; i < sequence.getChemModelCount(); i++) {
            IChemModel model = sequence.getChemModel(i);
            List models = ChemModelManipulator.getAllAtomContainers(model);
            for (int j = 0; j < models.size(); j++)
                container.add((IAtomContainer) models.get(j));

        }
        return container;
    }
    
    /**
     * Returns all the AtomContainer's of a ChemSequence.
     */
    public static List getAllAtomContainers(IChemSequence sequence) {
        Iterator models = sequence.chemModels();
        List acList = new ArrayList();
        while (models.hasNext()) {
    		IChemModel chemmodel = (IChemModel)models.next();
            acList.addAll(ChemModelManipulator.getAllAtomContainers(chemmodel));
        }
        return acList;
    }

	public static List getAllChemObjects(IChemSequence sequence) {
		ArrayList list = new ArrayList();
        list.add(sequence);
        for (int i=0; i<sequence.getChemModelCount(); i++) {
            list.addAll(ChemModelManipulator.getAllChemObjects(
            	sequence.getChemModel(i)
            ));
        }
		return list;
	}
}


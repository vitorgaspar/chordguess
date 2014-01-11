package pt.vgaspar.chordguess.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.vgaspar.chordguess.ButtonPlacing;
import pt.vgaspar.chordguess.IPackingAlgorithm;
import pt.vgaspar.chordguess.PackingAlgorithm;

public class PackingAlgorithmTests extends ChordGuessBaseTestCase {
	
	@Test
	public void PackingAlgorithm_WhenOnSquareFrameNoGap_PlaceButtonsCorrectly() {
		// Arrange
		IPackingAlgorithm packingAlgorithm = new PackingAlgorithm(20, 20, 4, 0);

		// Act
		ButtonPlacing placing1 = packingAlgorithm.getNext();
		ButtonPlacing placing2 = packingAlgorithm.getNext();
		ButtonPlacing placing3 = packingAlgorithm.getNext();
		ButtonPlacing placing4 = packingAlgorithm.getNext();
		
		// Assert
		assertEquals(0, placing1.x);
		assertEquals(0, placing1.y);
		assertEquals(10, placing1.width);
		assertEquals(10, placing1.height);
		
		assertEquals(10, placing2.x);
		assertEquals(0, placing2.y);
		assertEquals(10, placing2.width);
		assertEquals(10, placing2.height);
		
		assertEquals(0, placing3.x);
		assertEquals(10, placing3.y);
		assertEquals(10, placing3.width);
		assertEquals(10, placing3.height);
		
		assertEquals(10, placing4.x);
		assertEquals(10, placing4.y);
		assertEquals(10, placing4.width);
		assertEquals(10, placing4.height);
	}
	
	@Test
	public void PackingAlgorithm_WhenOnSquareFrameWithGap_PlaceButtonsCorrectly() {
		// Arrange
		IPackingAlgorithm packingAlgorithm = new PackingAlgorithm(20, 20, 4, 1);

		// Act
		ButtonPlacing placing1 = packingAlgorithm.getNext();
		ButtonPlacing placing2 = packingAlgorithm.getNext();
		ButtonPlacing placing3 = packingAlgorithm.getNext();
		ButtonPlacing placing4 = packingAlgorithm.getNext();
		
		// Assert
		assertEquals(1, placing1.x);
		assertEquals(1, placing1.y);
		assertEquals(8, placing1.width);
		assertEquals(8, placing1.height);
		
		assertEquals(11, placing2.x);
		assertEquals(1, placing2.y);
		assertEquals(8, placing2.width);
		assertEquals(8, placing2.height);
		
		assertEquals(1, placing3.x);
		assertEquals(11, placing3.y);
		assertEquals(8, placing3.width);
		assertEquals(8, placing3.height);
		
		assertEquals(11, placing4.x);
		assertEquals(11, placing4.y);
		assertEquals(8, placing4.width);
		assertEquals(8, placing4.height);
	}
	
	@Test
	public void PackingAlgorithm_WhenOnSquareFrameLotsOfButtonsAndGap_PlaceButtonsCorrectly() {
		// Arrange
		IPackingAlgorithm packingAlgorithm = new PackingAlgorithm(20, 20, 16, 2);

		// Act
		ButtonPlacing placing1 = packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		ButtonPlacing placing3 = packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		ButtonPlacing placing5 = packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		ButtonPlacing placing11 = packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		packingAlgorithm.getNext();
		ButtonPlacing placing16 = packingAlgorithm.getNext();
		
		// Assert
		assertEquals(2, placing1.x);
		assertEquals(2, placing1.y);
		assertEquals(1, placing1.width);
		assertEquals(1, placing1.height);
		
		assertEquals(12, placing3.x);
		assertEquals(2, placing3.y);
		assertEquals(1, placing3.width);
		assertEquals(1, placing3.height);
		
		assertEquals(2, placing5.x);
		assertEquals(7, placing5.y);
		assertEquals(1, placing5.width);
		assertEquals(1, placing5.height);
		
		assertEquals(12, placing11.x);
		assertEquals(12, placing11.y);
		assertEquals(1, placing11.width);
		assertEquals(1, placing11.height);
		
		assertEquals(17, placing16.x);
		assertEquals(17, placing16.y);
		assertEquals(1, placing16.width);
		assertEquals(1, placing16.height);
	}

	@Test
	public void PackingAlgorithm_WhenOnRealScreenDimensions_PlaceButtonsCorrectly() {
		// Arrange
		IPackingAlgorithm packingAlgorithm = new PackingAlgorithm(800, 480, 8, 5);

		// Act
		ButtonPlacing placing1 = packingAlgorithm.getNext();
		ButtonPlacing placing2 = packingAlgorithm.getNext();
		ButtonPlacing placing3 = packingAlgorithm.getNext();
		ButtonPlacing placing4 = packingAlgorithm.getNext();
		ButtonPlacing placing5 = packingAlgorithm.getNext();
		ButtonPlacing placing6 = packingAlgorithm.getNext();
		ButtonPlacing placing7 = packingAlgorithm.getNext();
		ButtonPlacing placing8 = packingAlgorithm.getNext();
		
		// Assert
		assertEquals(5, placing1.x);
		assertEquals(5, placing1.y);
		assertEquals(209, placing1.width);
		assertEquals(209, placing1.height);
		
		assertEquals(224, placing2.x);
		assertEquals(5, placing2.y);
		assertEquals(209, placing2.width);
		assertEquals(209, placing2.height);
		
		assertEquals(443, placing3.x);
		assertEquals(5, placing3.y);
		assertEquals(209, placing3.width);
		assertEquals(209, placing3.height);
		
		assertEquals(5, placing4.x);
		assertEquals(224, placing4.y);
		assertEquals(209, placing4.width);
		assertEquals(209, placing4.height);
		
		assertEquals(224, placing5.x);
		assertEquals(224, placing5.y);
		assertEquals(209, placing5.width);
		assertEquals(209, placing5.height);
		
		assertEquals(443, placing6.x);
		assertEquals(224, placing6.y);
		assertEquals(209, placing6.width);
		assertEquals(209, placing6.height);
		
		assertEquals(5, placing7.x);
		assertEquals(443, placing7.y);
		assertEquals(209, placing7.width);
		assertEquals(209, placing7.height);
		
		assertEquals(224, placing8.x);
		assertEquals(443, placing8.y);
		assertEquals(209, placing8.width);
		assertEquals(209, placing8.height);
	}
}

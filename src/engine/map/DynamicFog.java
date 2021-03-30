package engine.map;

public class DynamicFog {
	
	private boolean[][]fog;
	private int lineCount;
	private int columnCount;
	
	public DynamicFog(int lineCount, int columnCount) {
		this.setLineCount(lineCount);
		this.setColumnCount(columnCount);
		
		fog = new boolean[lineCount][columnCount];
		
		for (int lineIndex = 0; lineIndex < lineCount; lineIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				fog[lineIndex][columnIndex] = true;
			}
		}
	}

	public int getColumnCount() {
		return columnCount;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}
}

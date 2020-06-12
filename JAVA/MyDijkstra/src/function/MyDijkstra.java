/**
 * Copyright (C) 2018 Luvina
 * MyDijkstra.java Jun 25, 2019 HoangDinhThi
 */
package function;

/**
 * @author Dell
 *
 */
import java.util.ArrayList;



public class MyDijkstra {
	private int a[][];
	private int[] len, p;
	private int[][] logLen, logP;
	private boolean[] checkedPointMin; // diem co duong di ngan nhat (da xet)
	private int infinity, size = 0;
	private ArrayList<MyPoint> arrMyPoint = new ArrayList<MyPoint>();
	private ArrayList<MyLine> arrMyLine = new ArrayList<MyLine>();
	private ArrayList<Integer> arrPointResult;
	private ArrayList<Integer> arrPointResultStep;
	private ArrayList<Integer> arrCostResult = new ArrayList<Integer>();
	private int beginPoint = 0, endPoint = 0;
	private int numberPointChecked = 0;
	private boolean step = false;
	private boolean stop = false;
	private boolean mapType = false;
	private String path = "";
	private ArrayList<Integer> arrTempPoint;

	public MyDijkstra() {
	}

	public void input() {
		infinity = 1;
		size = arrMyPoint.size();
		// System.out.println(size);
		a = new int[size][size];
		len = new int[size];
		p = new int[size];
		checkedPointMin = new boolean[size];

		for (int i = 1; i < arrMyLine.size(); i++) {
			a[arrMyLine.get(i).getIndexPointA()][arrMyLine.get(i)
					.getIndexPointB()] = arrMyLine.get(i).getCost();
			if (!mapType) {
				a[arrMyLine.get(i).getIndexPointB()][arrMyLine.get(i)
						.getIndexPointA()] = arrMyLine.get(i).getCost();
			}
			infinity += arrMyLine.get(i).getCost();
		}
	}


	private void initValue() {
		logLen = new int[size][size];
		logP = new int[size][size];
		// processInput();
		for (int i = 1; i < size; i++) {
			len[i] = infinity;
			checkedPointMin[i] = false;
			p[i] = 0;
		}
		logLen[0] = len;
		logP[0] = p;
		len[beginPoint] = 0;
	}
	public void processInput() {
		for (int i = 1; i < size; i++) {
			for (int j = 1; j < size; j++) {
				if (a[i][j] == 0 && i != j) {
					a[i][j] = infinity;
				}
			}
		}
	}
	private boolean checkContinue(int k) {
		if (endPoint != -1) {
			return (!checkedPointMin[endPoint]);
		}
		return (k < arrMyPoint.size() - 1);
	}
	
	public int dijkstra() {
		initValue();
		int i = 1, k = 0;
		// for (int k = 1; k < size; k++) {
		while (checkContinue(k)) {
			for (i = 1; i < size; i++)
				if (!checkedPointMin[i] && len[i] < infinity) // tìm các điểm không phải giá trị  là vô cùng
					break;
			if (i >= size) // duyệt hết mà k tìm thây đỉnh đích
				break;
			for (int j = 1; j < size; j++) // tìm điểm có độ dài là min
				if (!checkedPointMin[j] && len[i] > len[j])
					i = j;

			checkedPointMin[i] = true; // cho i vao danh sach da xet r
			for (int j = 1; j < size; j++) { // tính lại độ dài của các đỉnh chưa xét
				if (!checkedPointMin[j] && len[i] + a[i][j] < len[j]) { 
					len[j] = len[i] + a[i][j]; // lưu kq
					p[j] = i; // dánh dấu điểm trk nó

				}
				logLen[k][j] = len[j];
				logP[k][j] = p[j];
			}
			k++;
		}
		if (endPoint == -1) { // endPoint = -1 -> beginPoint to all Point
			numberPointChecked = arrMyPoint.size();
			return 0;
		}
		numberPointChecked = k;
		return len[endPoint];
	}

	public int dijkstraStep(int step) {
		initValue();
		int i = 0, k = 0;
		arrPointResultStep = new ArrayList<Integer>();
		// while (!checkPointMin[end] && k < step) {
		while (checkContinueStep(step, k)) {
			for (i = 1; i < size; i++)
				if (!checkedPointMin[i] && len[i] < infinity)
					break;
			if (i >= size) {
				stop = true;
				break;
			}
			for (int j = 1; j < size; j++)
				if (!checkedPointMin[j] && len[i] > len[j])
					i = j;

			checkedPointMin[i] = true;
			for (int j = 1; j < size; j++) {
				if (!checkedPointMin[j] && len[i] + a[i][j] < len[j]) {
					len[j] = len[i] + a[i][j];
					p[j] = i;
				}
				logLen[k][j] = len[j];
				logP[k][j] = p[j];
			}
			arrPointResultStep.add(i);
			k++;
		}
		if (endPoint == -1) {
			numberPointChecked = arrMyPoint.size();
			return 0;
		}
		numberPointChecked = k;
		return len[endPoint];
	}

	private boolean checkContinueStep(int step, int k) {
		if (endPoint != -1) {
			return (!checkedPointMin[endPoint] && k < step);
		}
		return (k < arrMyPoint.size() - 1 && k < step);
	}

	

	public String tracePath() {
		path = "";
		if (endPoint > 0 && len[endPoint] < infinity) {
			int i = endPoint;
			while (i != beginPoint) {
				path = " --> " + i + path;
				i = p[i];
			}
			path = "The cost from " + beginPoint + " to " + endPoint + " is "
					+ len[endPoint] + "\t" + "Path : " + i + path;
		} else if (endPoint == -1) {
			for (int i = arrMyPoint.size() - 1; i >= 1; i--) {
				int j = i;
				if (len[i] < infinity) {
					while (j != beginPoint) {
						path = " --> " + j + path;
						j = p[j];
					}
					path = "The cost from " + beginPoint + " to " + i + " is "
							+ len[i] + "\t" + "Path : " + j + path;

				} else {
					path = "Can't go from " + beginPoint + " to " + i + path;
				}
				if (i > 1) {
					path = "\n" + path;
				}
			}
		} else {
			path = "Can't go from " + beginPoint + " to " + endPoint;
		}
		return path;
	}

	public String tracePathStep() {
		path = "";
		if (endPoint > 0) {
			int i = arrPointResultStep.get(arrPointResultStep.size() - 1);
			int j = i;
			while (j != beginPoint) {
				path = " --> " + j + path;
				j = p[j];
			}
			path = "The cost from " + beginPoint + " to " + i + " is " + len[i]
					+ "\t" + "Path : " + j + path;

			if (stop) {
				path = "Can't go from " + beginPoint + " to " + endPoint;
			}
		} else if (endPoint == -1) {
			for (int i = arrPointResultStep.size() - 1; i >= 0; i--) {
				int e = arrPointResultStep.get(i);
				int j = e;
				while (j != beginPoint) {
					path = " --> " + j + path;
					j = p[j];
				}
				path = "The cost from " + beginPoint + " to " + e + " is "
						+ len[e] + "\t" + "Path : " + j + path;

				if (stop) {
					path = "Can't go from " + beginPoint + " to " + endPoint;
				}
				if (i > 0) {
					path = "\n" + path;
				}
			}

		}

		return path;
	}

	public int getNumberPointChecked() {
		return numberPointChecked;
	}

	public void setNumberPointChecked(int numberPointChecked) {
		this.numberPointChecked = numberPointChecked;
	}

	public int[][] getLogLen() {
		return logLen;
	}

	public void setLogLen(int[][] logLen) {
		this.logLen = logLen;
	}

	public int[][] getLogP() {
		return logP;
	}

	public void setLogP(int[][] logP) {
		this.logP = logP;
	}

	public boolean isMapType() {
		return mapType;
	}

	public void setMapType(boolean mapType) {
		this.mapType = mapType;
	}

	public boolean[] getCheckedPointMin() {
		return checkedPointMin;
	}

	public void setCheckedPointMin(boolean[] checkedPointMin) {
		this.checkedPointMin = checkedPointMin;
	}

	public ArrayList<Integer> getArrPointResultStep() {
		return arrPointResultStep;
	}

	public void setArrPointResultStep(ArrayList<Integer> arrPointResultStep) {
		this.arrPointResultStep = arrPointResultStep;
	}

	public int[] getP() {
		return p;
	}

	public void setP(int[] p) {
		this.p = p;
	}

	public ArrayList<Integer> getArrTempPoint() {
		return arrTempPoint;
	}

	public void setArrTempPoint(ArrayList<Integer> arrTempPoint) {
		this.arrTempPoint = arrTempPoint;
	}

	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isStep() {
		return step;
	}

	public void setStep(boolean step) {
		this.step = step;
	}

	public int getInfinity() {
		return infinity;
	}

	public void setInfinity(int infinity) {
		this.infinity = infinity;
	}

	public int[] getLen() {
		return len;
	}

	public void setLen(int[] len) {
		this.len = len;
	}

	public int[][] getA() {
		return a;
	}

	public void setA(int[][] a) {
		this.a = a;
	}

	public int getBeginPoint() {
		return beginPoint;
	}

	public void setBeginPoint(int beginPoint) {
		this.beginPoint = beginPoint;
	}

	public int getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(int endPoint) {
		this.endPoint = endPoint;
	}

	public ArrayList<MyPoint> getArrMyPoint() {
		return arrMyPoint;
	}

	public void setArrMyPoint(ArrayList<MyPoint> arrMyPoint) {
		this.arrMyPoint = arrMyPoint;
	}

	public ArrayList<MyLine> getArrMyLine() {
		return arrMyLine;
	}

	public void setArrMyLine(ArrayList<MyLine> arrMyLine) {
		this.arrMyLine = arrMyLine;
	}

	public ArrayList<Integer> getArrPointResult() {
		return arrPointResult;
	}

	public void setArrPointResult(ArrayList<Integer> arrPointResult) {
		this.arrPointResult = arrPointResult;
	}

	public ArrayList<Integer> getArrCostResult() {
		return arrCostResult;
	}

	public void setArrCostResult(ArrayList<Integer> arrCostResult) {
		this.arrCostResult = arrCostResult;
	}

}



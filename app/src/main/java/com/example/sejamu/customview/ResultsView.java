package com.example.sejamu.customview;

import java.util.List;
import com.example.sejamu.tflite.Classifier.Recognition;

public interface ResultsView {
  public void setResults(final List<Recognition> results);
}

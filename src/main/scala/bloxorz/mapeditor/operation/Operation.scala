package bloxorz.mapeditor.operation

import bloxorz.mapeditor.EditingState

trait Operation {
  def operation(editingState: EditingState): EditingState
}
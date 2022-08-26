package bloxorz.mapeditor.operation

import bloxorz.mapeditor.EditingState

import scala.collection.mutable.ArrayBuffer

object CompositeOperation {

}


class CompositeOperation extends Operation {
  var children: ArrayBuffer[Operation] = ArrayBuffer[Operation]()

  override def operation(editingState: EditingState): EditingState = {
    var result = editingState
    for (child <- children) {
      result = child.operation(result)
    }
    result
  }

  def add(child: Operation): CompositeOperation = {
    children += child
    this
  }


  def remove(child: Operation): ArrayBuffer[Operation] = {
    children -= child
  }

  def getChild(index: Int): Operation = {
    if (index > 0 && children.size > index) children(index) else null
  }


}

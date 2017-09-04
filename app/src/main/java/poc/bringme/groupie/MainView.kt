package poc.bringme.groupie

import poc.bringme.groupie.interactor.Callbacks

interface MainView : Callbacks.SwipeCallback {

    fun onRefresh()
    fun onUndoRemove(payload: Callbacks.SwipeRemovePayload)
    fun onFabClick()
    fun onAddGroupClicked()
}

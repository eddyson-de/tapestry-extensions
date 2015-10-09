define ["t5/core/dom", "t5/core/events"], (dom, events) ->

  className = 'zone-updating'
  (clientId)->
    el = dom clientId
    el.on events.zone.refresh, ->
      @addClass className
      return
    el.on events.zone.didUpdate, ->
      @removeClass className
      return
    return

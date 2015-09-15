define ["t5/core/dom", "jquery", "t5/core/ajax", "t5/core/console", "underscore"], (dom, $, ajax, console, _) ->
  bodyscrolled = (bodyWrapper, url) ->
    tableBody = bodyWrapper.find 'tbody'
    return if tableBody.data 'all-rows-shown'
    spaceToBottom = tableBody.height() - bodyWrapper.scrollTop() - bodyWrapper.height()
    if spaceToBottom < 150
      addMoreContent tableBody, url
    return
    
  addMoreContent = (tableBody, url)->
    ajax url,
         data : (from:(tableBody.find 'tr').length)
         success: (response)->
           (
             console.debug "Received row : #{row}"
             newRow = (tableBody.find 'tr').eq(0).clone()
             newRow.attr 'data-grid-row', null
             for col,i in row
               td = ((newRow.find 'td').eq i)
               td.html col 
               #console.debug "Adding col #{i}: #{col}"
             tableBody.append newRow
           ) for row in response.json.rows
           tableBody.data 'all-rows-shown', true unless response.json.more
           return
  
  init = (id, url) ->
    table = ($ '#'+id)
    tbody = (table.find 'tbody')
    tableHeight = table.outerHeight true
    headHeight = (table.find 'thead').height()
    headWrapper = $ '<div class="infinigridHead"/>'
    table.wrap '<div/>'
    bodyWrapper = table.parent()
    
    tableCopy = table.clone()
    tableCopy.removeAttr 'id'
    
    bodyWrapper.before headWrapper
    headWrapper.append tableCopy
    thWidths = (($ th).outerWidth() for th in ($ tableCopy).find 'th')
    (
      for row in (table.find 'tr').eq 0
        ((($ row).find 'td,th').eq i).width thWidths[i]
      ($ th).width thWidths[i]
      
    ) for th,i in ($ tableCopy).find 'th'
    (tableCopy.find 'tbody').remove()
    tbl.css 'table-layout', 'fixed' for tbl in [tableCopy, table]
    headWrapper.css height: headHeight
    (table.find 'thead th').html('')
    bodyWrapper.css 'paddingBottom', "+=#{table.css 'marginBottom'}"
    table.css 'marginBottom', 0
    bodyWrapper.addClass 'infinigridBody'
    bodyWrapper.css 'height', tableHeight - headHeight
    
    bodyWrapper.scroll _.debounce (-> bodyscrolled bodyWrapper, url; return), 100
    
    
    addMoreContent (bodyWrapper.find 'tbody'), url
    
    return
  init
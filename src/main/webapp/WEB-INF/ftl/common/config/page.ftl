<#if page??>
    <div class="text-center">
        <div class="paginationDiv">
            <ul class="pagination">
                <li class="${(!page.hasPrev)?string('disabled','')}">
                    <a href="?start=0${page.param!}">&laquo;</a>
                </li>
                <li class="${(!page.hasPrev)?string('disabled','')}">
                    <a href="?start=${page.start-page.count}${page.param!}">&lsaquo;</a>
                </li>

                <#list 0..(page.totalPage-1) as i>
                    <#if (page.start - i * page.count) <= 15 && (i * page.count - page.start) <= 15>
                        <li class="${(i*page.count == page.start)?string('disabled','')}">
                            <a href="?start=${i*page.count}${page.param!}"
                               class="${(i*page.count==page.start)?string('active','')}">
                                ${i+1}
                            </a>
                        </li>
                    </#if>
                </#list>

                <li class="${(!page.hasNext)?string('disabled','')}">
                    <a href="?start=${page.start+page.count}${page.param!}">&rsaquo;</a>
                </li>
                <li class="${(!page.hasNext)?string('disabled','')}">
                    <a href="?start=${page.lastStart}${page.param!}">&raquo;</a>
                </li>
            </ul>
        </div>
    </div>
    <script>
        $(function () {
            $('.pagination li.disabled a').click(function () {
                return false
            })
        })
    </script>
</#if>
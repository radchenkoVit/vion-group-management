---
to: "<%
 const basePath = 'src/design-system/entities/';
 const componentFolder = type + 's/';
 const prefix = type === 'unique-organism' ? 'o-' : type[0] + '-';
 const componentName =  prefix + h.changeCase.kebab(name);
 return basePath + componentFolder + componentName + '/' + componentName + '.vue' %>"
---
<%
    const prefix = type === 'unique-organism' ? 'o-' : type[0] + '-';
    const componentName = {
        kebab: h.changeCase.kebab(prefix + name),
        pascal: h.changeCase.pascal(prefix + name)
    }
%><template>
    <div
        at-<%= componentName.kebab %>
        class="<%= componentName.kebab %>">
        This is <%= componentName.pascal %>
    </div>
</template>

<script>
export default {
    name: '<%= componentName.pascal %>'
};
</script>

<style lang="scss">
    @import 'theme';
</style>

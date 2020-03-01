---
to: "<%
 const basePath = 'src/design-system/entities/';
 const componentFolder = type + 's/';
 const prefix = type === 'unique-organism' ? 'o-' : type[0] + '-';
 const componentName =  prefix + h.changeCase.kebab(name);
 return type === 'page' ? basePath + componentFolder + componentName + '/index.js' : null %>"
---
<%
    const prefix = type === 'unique-organism' ? 'o-' : type[0] + '-';
    const componentName = {
        kebab: h.changeCase.kebab('p-' + name),
        pascal: h.changeCase.pascal('p-' + name)
    }
%>import Vue from 'vue';
import iView from 'iview';
import VTooltip from 'v-tooltip';
import locale from 'iview/dist/locale/en-US';
import { Loading } from '../../../../vue/directives/loading';
import { Permissions } from '../../../../vue/directives/permissions';

import <%= componentName.pascal %> from './<%= componentName.kebab %>.vue';

Vue.use(VTooltip);
Vue.use(iView, { locale });
Vue.use(Loading);
Vue.use(Permissions);

// eslint-disable-next-line no-new
new Vue({
    el: '#vue-<%= componentName.kebab %>',
    render: r => r(<%= componentName.pascal %>)
});

---
to: "<%
 const basePath = 'src/design-system/entities/';
 const componentFolder = type + 's/';
 const prefix = type === 'unique-organism' ? 'o-' : type[0] + '-';
 const componentName =  prefix + h.changeCase.kebab(name);
 return basePath + componentFolder + componentName + '/theme.scss' %>"
---
<%
    const prefix = type === 'unique-organism' ? 'o-' : type[0] + '-';
    const componentName = h.changeCase.kebab(prefix + name);
%>@import '../../../common/scss/ds-variables';

.<%= componentName %> {
  display: block;
}

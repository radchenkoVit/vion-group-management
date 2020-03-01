<template>
  <div
    :class="['m-input-wrapper', { 'error': error }]"
    :at-m-input-wrapper="title">
    <div
      v-if="title || isShowEmptyTitle"
      class="input-title">
      {{ title }}
    </div>
    <div class="input-value">
      <slot />
    </div>
    <div
      v-if="error"
      :title="error"
      class="input-error">
      {{ error }}
    </div>
  </div>
</template>

<script>
  /* eslint-disable */
  export default {
    name: 'MInputWrapper',
    props: {
      title: {
        type: String,
        default: ''
      },
      value: {
        type: String,
        default: ''
      },
      maxLength: {
        type: [String, Number],
        default: ''
      },
      error: {
        type: String,
        default: ''
      },
      isShowEmptyTitle: {
        type: Boolean,
        default: false
      }
    }
  };
</script>

<style lang="scss">
  @import 'theme';
</style>

<template>
  <input
    v-model="localValue"
    :at-a-input-text="atAttribute"
    :disabled="disabled"
    :placeholder="placeholder"
    :maxlength="maxLength"
    type="text"
    class="a-text-input"
    autocomplete="new-password"
    @keyup.enter="onKeyUpEnter">
</template>

<script>
  /* eslint-disable */
  export default {
    name: 'AInput',
    $_veeValidate: {
      name() {
        return this.name;
      },
      value() {
        return this.value;
      }
    },
    props: {
      value: {
        type: [String, Number],
        default: ''
      },
      placeholder: {
        type: String,
        default: ''
      },
      disabled: {
        type: Boolean,
        default: false
      },
      maxLength: {
        type: [String, Number],
        default: ''
      },
      atAttribute: {
        type: String,
        default: ''
      }
    },
    data() {
      return {
        localValue: this.value
      };
    },
    watch: {
      localValue(value) {
        this.$emit('input', value);
      },
      value: {
        handler(value) {
          this.localValue = value;
        }
      }
    },
    methods: {
      onKeyUpEnter() {
        this.$emit('onKeyUpEnter', this.localValue);
      }
    }
  };
</script>

<style lang="scss">
  @import 'theme';
</style>

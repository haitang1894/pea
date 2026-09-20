<script setup lang="ts">
interface Props {
  id: string;
}

defineProps<Props>();

const searchParams = { current: 1 };

const getData = () => {
  // TODO: load data
};

const getSearch = () => {
  searchParams.current = 1;
  getData();
};
</script>

<template>
  <LookForward />
</template>

<style scoped></style>

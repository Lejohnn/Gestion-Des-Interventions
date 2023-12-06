<script setup>
import { useRoute } from 'vue-router'
import Categorie from './categorie.vue'
import sousCategorie from './sousCategorie.vue'
import etudiant from './etudiant.vue'
import personnel from './personnel.vue'
import demande from './demande.vue'
import departements from './departements.vue'
import mails from './mails.vue'
import Swal from 'sweetalert2'

const route = useRoute()
const activeTab = ref(route.params.tab)

// tabs
const tabs = [
  {
    title: 'Demandes',
    icon: 'mdi-book-open-page-variant-outline',
    tab: 'demande',
  },
]
// const vues = ref();
// let result;
// let account = JSON.parse(localStorage.getItem('account'))
// if (account.created_by === "etudiant") {
//   result =  true
// } else {
//   result =  false
// }

// if (account== null) {
//   location.assign("http://localhost:5173/login")
// }
// console.log(result)

// tabs
const tabs1 = [
  {
    title: 'Catégories',
    icon: 'mdi-account-hard-hat-outline',
    tab: 'categorie',
  },
  {
    title: 'Sous-catégories',
    icon: 'mdi-alarm-light',
    tab: 'sousCategorie',
  },
  {
    title: 'Départements',
    icon: 'mdi-home-outline',
    tab: 'departements',
  },
  {
    title: 'Etudiants',
    icon: 'mdi-account-graduation-outline',
    tab: 'etudiant',
  },
  {
    title: 'Personnels',
    icon: 'mdi-account-pilot',
    tab: 'personnel',
  },
  {
    title: 'Demandes',
    icon: 'mdi-book-open-page-variant-outline',
    tab: 'demande',
  },
]
</script>

<template>
  <div>
    <VTabs
      v-model="activeTab"
      show-arrows
      class="v-tabs-pill"
    >
      <span v-if="result">
        <VTab
          v-for="item in tabs"
          :key="item.icon"
          :value="item.tab"
        >
          <VIcon
            size="20"
            start
            :icon="item.icon"
          />
          {{ item.title }}
        </VTab>
      </span>

      <span v-if="!result">
        <VTab
          v-for="item in tabs1"
          :key="item.icon"
          :value="item.tab"
        >
          <VIcon
            size="20"
            start
            :icon="item.icon"
          />
          {{ item.title }}
        </VTab>
      </span>
    </VTabs>

    <VWindow
      v-model="activeTab"
      class="mt-5 disable-tab-transition"
    >
      <!-- Account -->
      <VWindowItem value="categorie">
        <Categorie />
      </VWindowItem>

      <VWindowItem value="sousCategorie">
        <sousCategorie />
      </VWindowItem>

      <VWindowItem value="departements">
        <departements />
      </VWindowItem>

      <VWindowItem value="etudiant">
        <etudiant />
      </VWindowItem>

      <VWindowItem value="personnel">
        <personnel />
      </VWindowItem>

      <VWindowItem value="demande">
        <demande />
      </VWindowItem>

      <VWindowItem value="mails">
        <mails />
      </VWindowItem>
    </VWindow>
  </div>
</template>
